package ch.i4ds.des.mb.classification.algorithms.weka;

import ch.i4ds.annotation.StartupActivated;
import ch.i4ds.des.Controller;
import ch.i4ds.des.datastructure.geomcomp.GeomComponent;
import ch.i4ds.des.datastructure.geomcomp.GeomComponentType;
import ch.i4ds.des.mb.classification.DecisionInfo;
import ch.i4ds.des.mb.classification.algorithms.Classifier;
import ch.i4ds.des.mb.classification.algorithms.Selectable;
import ch.i4ds.des.mb.classification.attributes.Attribute;
import ch.i4ds.logging.LogProvider;
import java.util.List;
import java.util.Set;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Lets the user choose any of the Weka classification algorithms to classify
 * <CODE>GeomComponent</CODE>s.<br/> <img src="{@docRoot}/ch/i4ds/des/mb/classification/doc-files/figs/Weka_Logo.png"
 * alt="Weka logo"/>
 * 
 * @author Robin Vobruba [robin.vobruba@students.fhnw.ch]
 * @since 20071024
 */
@StartupActivated
@Selectable
public class WekaClassifier implements Classifier {

	public static final String NAME = "Weka Classifier";
	public static final Class<? extends weka.classifiers.Classifier> DEFAULT_WEKA_CLASSIFIER = weka.classifiers.trees.J48.class;

	private Controller controller;
	private weka.classifiers.Classifier classifier;
	private List<Attribute> attributes;
	private FastVector wekaAttributes;
	private List<GeomComponentType> types;
	private DecisionInfo decisionInfo;
	private Set<GeomComponent> sampleGeomComponents;
	private Instances mainDataSet;

	public WekaClassifier(Controller controller) {

		this.controller = controller;

		setWekaClassifier(getDefaultWekaClassifier());
	}

	public static weka.classifiers.Classifier getDefaultWekaClassifier() {

		try {
			return DEFAULT_WEKA_CLASSIFIER.newInstance();
		}
		catch (Exception ex) {
			return new weka.classifiers.rules.ZeroR();
		}
	}

	public void setWekaClassifier(weka.classifiers.Classifier classifier) {

		this.classifier = classifier;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public void setTypes(List<GeomComponentType> types) {
		this.types = types;
	}

	public void setSampleGeomComponents(Set<GeomComponent> sampleGeomComponents) {
		this.sampleGeomComponents = sampleGeomComponents;
	}

	public void initialize() {
		train();
	}

	public void edit() {

		ClassifierChooser cc = new ClassifierChooser();
		cc.setClassifier(classifier);
		if (cc.showDialog())
			classifier = cc.getClassifier();
	}

	public boolean isEditable() {
		return true;
	}

	/**
	 * Trains the weka classifiers with the samples. Samples are those <CODE>GeomComponent</CODE>s
	 * that are assigned to a type (by the user).
	 */
	public void train() {

		try {
			Instances trainingDataSet = null;

			createWekaAttributes();

			// check if everything is ready to train
			if (classifier == null) {
				throw new IllegalStateException(
						"can not train; classifier is not set");
			}
			if (attributes == null || attributes.size() == 0) {
				throw new IllegalStateException(
						"can not train; no attributes are set");
			}
			if (sampleGeomComponents == null
					|| sampleGeomComponents.size() == 0) {
				throw new IllegalStateException("can not train; no samples"
						+ " (some GeoComponents have to have their type set)");
			}
			// prepare the training-dataset
			trainingDataSet = new Instances("traininggeomcomponents",
					wekaAttributes, sampleGeomComponents.size());
			mainDataSet = new Instances("geomcomponents", wekaAttributes, 0);
			trainingDataSet.setClassIndex(0);
			mainDataSet.setClassIndex(0);
			for (GeomComponent sampleComponent : sampleGeomComponents) {
				trainingDataSet.add(encodeComponent(sampleComponent, true));
			}

			// do the actual training
			classifier.buildClassifier(trainingDataSet);
		}
		catch (Exception ex) {
			LogProvider.getSystemLogger().error("training failed", ex);
		}
	}

	public GeomComponentType classify(GeomComponent gc) {

		GeomComponentType type = null;

		try {
			Instance encodedComponent = encodeComponent(gc, false);
			double[] classDistribution = classifier
					.distributionForInstance(encodedComponent);
			int typeIndex = findMaxIndex(classDistribution);
			double certainity = classDistribution[typeIndex];
			String reason = "";

			type = types.get(typeIndex);
			decisionInfo = new DecisionInfo(NAME, certainity, reason);
		}
		catch (Exception ex) {
			LogProvider.getSystemLogger().warn("classification failed", ex);
		}

		return type;
	}

	public DecisionInfo getDecisionInfo() {
		return decisionInfo;
	}

	/**
	 * Encodes a <CODE>GeomComponent</CODE> into a Weka <CODE>Instance</CODE>.
	 * 
	 * @param gc
	 *            the component to encode
	 * @param withType
	 *            wether or not to encode the type aswell, or leave blank
	 * @return the Weka encoded version of the supplied geometric component
	 */
	private Instance encodeComponent(GeomComponent gc, boolean withType) {

		Instance encodedComponent = null;

		int numValues = wekaAttributes.size();
		encodedComponent = new Instance(numValues);
		mainDataSet.add(encodedComponent);
		encodedComponent.setDataset(mainDataSet);

		if (withType) {
			encodedComponent.setValue(0, gc.getGeomComponentType().getName());
		}

		for (int val = 1, attr = 0; val < numValues; attr++) {
			encodedComponent.setValue(val, 0.0);
			Attribute attribute = attributes.get(attr);
			try {
				if (attribute.supportsAffinityCalculation()) {
					for (int t = 0; t < types.size(); t++) {
						encodedComponent.setValue(val, 0.0);
						encodedComponent.setValue(val++, attribute
								.calculateAffinity(gc, types.get(t)));
					}
				}
				else {
					encodedComponent.setValue(val, 0.0);
					encodedComponent.setValue(val++, attribute
							.calculateValue(gc));
				}
			}
			catch (Exception ex) {
				LogProvider.getSystemLogger().error(
						"attribute extraction failed: "
								+ attributes.get(attr).toString(), ex);
			}
		}

		return encodedComponent;
	}

	/**
	 * Creates the Weka attributes. This depends on the occuring <CODE>GeomComponentType</CODE>s
	 * and the attributes tha tshall be used for classification.
	 * 
	 * @param gc
	 *            the component to encode
	 * @param withType
	 *            wether or not to encode the type aswell, or leave blank
	 * @return the Weka encoded version of the supplied geometric component
	 */
	private void createWekaAttributes() {

		FastVector componentTypesVec = new FastVector();

		for (GeomComponentType type : types) {
			componentTypesVec.addElement(type.getName());
		}

		weka.core.Attribute componentType = new weka.core.Attribute(
				"componetType", componentTypesVec);

		wekaAttributes = new FastVector();
		wekaAttributes.addElement(componentType);

		for (Attribute attribute : attributes) {
			attribute.setController(controller);

			// calculate number of attributes/values for Weka
			// (unfold affinity Attributes from 1 to <numberOfTypes>)
			if (attribute.supportsAffinityCalculation()) {
				for (int i = 0; i < types.size(); i++)
					wekaAttributes.addElement(new weka.core.Attribute(attribute
							.toString()
							+ "_" + types.get(i).getName()));
			}
			else
				wekaAttributes.addElement(new weka.core.Attribute(attribute
						.toString()));
		}
	}

	@Override
	public String toString() {

		if (classifier == null) {
			return NAME;
		}
		else {
			return NAME + ": " + classifier.getClass().getSimpleName() + " "
					+ concat(classifier.getOptions());
		}
	}

	private static String concat(String[] strings) {

		String compact = (strings.length > 0) ? strings[0] : "";
		for (int i = 1; i < strings.length; i++) {
			compact += " " + strings[i];
		}

		return compact.trim();
	}

	/**
	 * Returns the index of the array component with the highest value.
	 * 
	 * @param values
	 *            the values to search through
	 * @return the index of the array component with the highest value.
	 */
	public static int findMaxIndex(double[] values) {

		int maxIndex = 0;

		double max = Double.MIN_VALUE;
		for (int i = 0; i < values.length; i++) {
			if (values[i] > max) {
				max = values[i];
				maxIndex = i;
			}
		}

		return maxIndex;
	}

	public static int findMaxIndex(List<Double> values) {

		int maxIndex = 0;

		double max = Double.MIN_VALUE;
		for (int i = 0; i < values.size(); i++) {
			if (values.get(i) > max) {
				max = values.get(i);
				maxIndex = i;
			}
		}

		return maxIndex;
	}
}
