package planner;

import planner.exceptions.CircuitInRouteException;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RouteBuilder {


    private TreeNode root;
    private Queue<TreeNode> queue;
    private StringBuilder stringBuilder;

    public String plan(List<PlaceAndCheaperPlace> places) throws CircuitInRouteException {
        root = new TreeNode(null);

        for (PlaceAndCheaperPlace place : places) {
            // place => dependentPlace ( x => y )
            String placeName = place.getPlaceName();
            String dependentPlaceName = place.getDependentPlaceName();

            TreeNode placeNode = findPlaceInTree(root, placeName);
            TreeNode dependentPlaceNode = findPlaceInTree(root, dependentPlaceName);

            checkForCircuit(placeNode, dependentPlaceNode);

            //If placeNode does not exist in the tree yet, create it directly under the root of the tree.
            if (placeNode == null) {
                placeNode = new TreeNode(placeName, root);
            }

            //If dependentPlaceNode does not exist in the tree yet, create it directly under the root of the tree.
            if (dependentPlaceName != null && dependentPlaceNode == null) {
                dependentPlaceNode = new TreeNode(dependentPlaceName, root);
            }

            //If dependentPlaceNode exist, add the placeNode to it's children.
            if (dependentPlaceNode != null) {
                placeNode.setParent(dependentPlaceNode);
            }


        }
        return getRoute();
    }

    /* (placeNode => dependentPlaceNode)
    If both nodes exists in the tree and placeNode is on a higher level than dependentPlaceNode
    then setting dependentPlaceNode as the parent node of placeNode would violate other rules.

    For example x => y , y => z , z => x, after the first two rules the plan is: zyx, placing x before z would violate other rules.
    */
    private void checkForCircuit(TreeNode placeNode, TreeNode dependentPlaceNode) throws CircuitInRouteException {
        if (placeNode == null || dependentPlaceNode == null) {
            return;
        }
        int placeNodeLevel = placeNode.getLevel();
        int dependentPlaceNodeLevel = dependentPlaceNode.getLevel();
        if (placeNodeLevel < dependentPlaceNodeLevel) {
            throw new CircuitInRouteException();
        }
    }


    private TreeNode findPlaceInTree(TreeNode node, String placeName) {
        if (node.place != null && node.place.equals(placeName)) {
            return node;
        }
        List<TreeNode> children = node.getChildren();
        for (TreeNode child : children) {
            TreeNode res = findPlaceInTree(child, placeName);
            if (res != null) {
                return res;
            }
        }
        return null;
    }

    private String getRoute() {
        queue = new LinkedList<>();
        stringBuilder = new StringBuilder();
        breadthFirstTraverse();
        return stringBuilder.toString();
    }

    private void breadthFirstTraverse() {
        queue.clear();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.remove();
            if (node.place != null && !node.place.isEmpty()) {
                stringBuilder.append(node.place);
            }
            queue.addAll(node.getChildren());
        }

    }


}
