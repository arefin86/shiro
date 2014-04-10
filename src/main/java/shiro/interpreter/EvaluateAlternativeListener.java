package shiro.interpreter;

import java.util.HashMap;
import java.util.Map;
import shiro.Node;
import shiro.SubjunctiveNode;
import shiro.SubjunctiveParametricSystem;
import shiro.definitions.SystemState;

/**
 * Walk the parse tree of an alternative to evaluate it.
 * @author jeffreyguenther
 */
public class EvaluateAlternativeListener extends ShiroBasePassListener{
    private Map<SubjunctiveNode, Node> subjunctTable;
    private String graphName = "<empty>";

    public EvaluateAlternativeListener(SubjunctiveParametricSystem pSystem) {
        super(pSystem);
        subjunctTable = new HashMap<SubjunctiveNode, Node>();
    }

    @Override
    public void exitStatestmt(ShiroParser.StatestmtContext ctx) {
        String stateName  = ctx.stateName().getText();
        
        SystemState state = new SystemState(pSystem.getGraphDef(graphName), stateName);
        state.setActiveNode(subjunctTable);
       
       pSystem.addAlternative(state); 
    }

    @Override
    public void enterStateGraph(ShiroParser.StateGraphContext ctx) {
        graphName = ctx.IDENT().getText();
    }
    
    

    @Override
    public void enterActivation(ShiroParser.ActivationContext ctx) {
       String nodeName = ctx.nodeName.getText();
       String activeObject = ctx.activeObject.getText();
       
       SubjunctiveNode sn = pSystem.getSubjunctiveNode(nodeName);
       Node n = pSystem.getNode(activeObject);
       
       subjunctTable.put(sn, n);
    }
}
