package ch.i4ds.des.mb.classification.algorithms.weka;

import ch.i4ds.gui.helper.ModalDialog;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import weka.classifiers.Classifier;
import weka.core.SerializedObject;
import weka.gui.GenericObjectEditor;

/**
 * A Dialog that lets the user choose a Weka classification algorithm.
 * 
 * @author Robin Vobruba [robin.vobruba@students.fhnw.ch]
 * @since 20071101
 */
public class ClassifierChooser extends GenericObjectEditor {

	private Classifier classifier;

	public Classifier getClassifier() {
		return classifier;
	}

	public void setClassifier(Classifier classifier) {

		this.classifier = classifier;
		this.setValue(classifier);
	}

	public ClassifierChooser() {

		setClassType(Classifier.class);
		setValue(new weka.classifiers.rules.ZeroR());
		// addPropertyChangeListener(new PropertyChangeListener() {
		// public void propertyChange(PropertyChangeEvent e) {
		// repaint();
		// }
		// });
		((GenericObjectEditor.GOEPanel)getCustomEditor())
				.addOkListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						classifier = (Classifier)copyObject(getValue());
					}
				});
	}

	public static void main(String[] args) {

		ClassifierChooser cc = new ClassifierChooser();
		// JFrame frame = new JFrame();
		// frame.getContentPane().add(cc.getCustomPanel());
		// frame.setVisible(true);
		// frame.setSize(800, 600);
		cc.showDialog();
	}

	/**
	 * Shows a dialog to the user where he can select a Weka classification
	 * algorithm.
	 * 
	 * @return <CODE>true</CODE> if the user pressed OK, <CODE>false</CODE>
	 *         in every other case.
	 */
	public boolean showDialog() {

		Object message = getCustomPanel();
		String title = "Choose Classification Algorithm";
		int optionType = JOptionPane.OK_CANCEL_OPTION;
		int messageType = JOptionPane.PLAIN_MESSAGE;

		int ret = ModalDialog.showOptionDialog(title, null, getCustomPanel(),
				null, false);
		// JOptionPane op = new JOptionPane(message, messageType, optionType,
		// null);
		// JDialog dialog = op.createDialog(null, title);
		// // dialog.setModal(false);
		// dialog.setModal(true);
		// // dialog.setModalityType(ModalityType.TOOLKIT_MODAL);
		// // dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		// // dialog.setModalityType(ModalityType.DOCUMENT_MODAL);
		// // dialog.setModalityType(ModalityType.MODELESS);
		//		
		// // show dialog
		// dialog.setVisible(true);
		// // wait till the dialog is not visible anymore
		// dialog.dispose();
		//		
		// if (op.getValue() instanceof Integer &&
		// ((Integer) op.getValue()) == JOptionPane.OK_OPTION) {
		// classifier = (Classifier) this.getValue();
		// return true;
		// }
		// else
		return false;
	}

	/**
	 * Makes a copy of an object using serialization.
	 * 
	 * @param source
	 *            the object to copy
	 * @return a copy of the source object
	 */
	protected Object copyObject(Object source) {

		Object result = null;
		try {
			SerializedObject so = new SerializedObject(source);
			result = so.getObject();
		}
		catch (Exception ex) {
			System.err.println("AlgorithmListPanel: Problem copying object");
			System.err.println(ex);
		}
		return result;
	}

}
