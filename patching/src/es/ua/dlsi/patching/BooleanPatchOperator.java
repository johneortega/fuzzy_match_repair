/**
 * 
 */
package es.ua.dlsi.patching;

import es.ua.dlsi.segmentation.Segment;
import es.ua.dlsi.utils.Pair;

/**
 * @author johneortega
 *
 */
public class BooleanPatchOperator {
	public boolean isYesNode=false;
	public boolean isNoNode=false;
	public PatchOperator patchOperator = null;

	/**
	 * @param origPos
	 * @param words
	 */
	public BooleanPatchOperator(PatchOperator patchOperator, boolean isYesNode, boolean isNoNode) {
		this.patchOperator = patchOperator;
		this.isYesNode=isYesNode;
		this.isNoNode=isNoNode;
	}

	public boolean isYesNode() {
		return isYesNode;
	}

	public void setYesNode(boolean isYesNode) {
		this.isYesNode = isYesNode;
	}

	public boolean isNoNode() {
		return isNoNode;
	}

	public void setNoNode(boolean isNoNode) {
		this.isNoNode = isNoNode;
	}

	public PatchOperator getPatchOperator() {
		return patchOperator;
	}

	public void setPatchOperator(PatchOperator patchOperator) {
		this.patchOperator = patchOperator;
	}

}
