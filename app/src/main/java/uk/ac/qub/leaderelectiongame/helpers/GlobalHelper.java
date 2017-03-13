package uk.ac.qub.leaderelectiongame.helpers;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import uk.ac.qub.leaderelectiongame.consts.Consts;
import uk.ac.qub.leaderelectiongame.model.Node;

public class GlobalHelper {

    public static String boolToString(boolean value) {
        if (value) {
            return Consts.YES;
        }
        return Consts.NO;
    }

    public static String flatIdsFromNodeList(List<Node> nodes) {
        if (nodes == null) {
            return Consts.BLANK_STRING_VALUE;
        }
        if (nodes.size() == 0) {
            return Consts.BLANK_STRING_VALUE;
        }
        String result = "";
        for (Node node: nodes) {
            if (result != "") {
                result += "; ";
            }
            result += String.valueOf(node.getId());
        }   //for
        return result;
    }

}
