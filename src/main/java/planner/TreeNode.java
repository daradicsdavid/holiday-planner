package planner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TreeNode {

    public final String place;
    private TreeNode parent;
    private final List<TreeNode> children;

    TreeNode(String place) {
        this.place = place;
        children = new ArrayList<>();
    }

    TreeNode() {
        place = null;
        children = new ArrayList<>();
    }

    public TreeNode getParent() {
        return parent;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setParent(TreeNode parent) {
        if (this.parent != null) {
            this.parent.getChildren().remove(this);
        }
        this.parent = parent;
        if (!parent.getChildren().contains(this)) {
            parent.addChild(this);
        }
    }

    private void addChild(TreeNode child) {
        this.children.add(child);
    }

    private boolean isRoot() {
        return parent == null;
    }

    public int getLevel() {
        if (this.isRoot())
            return 0;
        else
            return parent.getLevel() + 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeNode treeNode = (TreeNode) o;
        return Objects.equals(place, treeNode.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(place);
    }
}