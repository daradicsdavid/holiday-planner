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
            String placeName = place.getPlaceName();
            String dependentPlaceName = place.getDependentPlaceName();

            TreeNode placeNode = findPlaceInTree(root, placeName);
            TreeNode dependentPlaceNode = findPlaceInTree(root, dependentPlaceName);


            if (placeNode == null) {
                placeNode = new TreeNode(placeName, dependentPlaceNode == null ? root : dependentPlaceNode);
            }

            if (dependentPlaceName != null) {
                if (dependentPlaceNode == null) {
                    dependentPlaceNode = new TreeNode(dependentPlaceName, root);
                }

                checkForCircuit(placeNode, dependentPlaceNode);

                placeNode.setParent(dependentPlaceNode);
            }


        }
        return getRoute();
    }

    private void checkForCircuit(TreeNode placeNode, TreeNode dependentPlaceNode) throws CircuitInRouteException {
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
