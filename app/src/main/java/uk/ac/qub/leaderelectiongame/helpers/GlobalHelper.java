package uk.ac.qub.leaderelectiongame.helpers;

import android.text.TextUtils;

import java.util.List;

import uk.ac.qub.leaderelectiongame.consts.Consts;
import uk.ac.qub.leaderelectiongame.model.Node;

/**
 * Class responsible for grouping common application functions.
 */
public class GlobalHelper {

    /**
     * Method converting boolean to string.
     * @param value
     * @return yes or no
     */
    public static String boolToString(boolean value) {
        if (value) {
            return Consts.YES;
        }
        return Consts.NO;
    }

    /**
     * Method flattening ids to string from nodes list.
     * @param nodes
     * @return flattened ids as string
     */
    public static String flatIdsFromNodeList(List<Node> nodes) {
        if (nodes == null) {
            return Consts.BLANK_STRING_VALUE;
        }
        if (nodes.size() == 0) {
            return Consts.BLANK_STRING_VALUE;
        }
        String result = "";
        for (Node node: nodes) {
            if (!TextUtils.isEmpty(result)) {
                result += "; ";
            }
            result += String.valueOf(node.getId());
        }   //for
        return result;
    }

}
