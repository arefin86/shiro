/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package shiro.definitions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

/**
 *
 * @author jeffreyguenther
 */
public class NodeDefinition implements Definition{
    private String name;
    private String activeUpdatePort;
    private boolean hasActiveUpdatePort;
    private Comment beginInLineComment;
    private List<PortDeclaration> ports;
    private Comment endInLineComment;

    public NodeDefinition(String name) {
        this.name = name;
        this.activeUpdatePort = null;
        this.hasActiveUpdatePort = false;
        this.ports = new ArrayList<>();
        this.beginInLineComment = null;
        this.endInLineComment = null;
    }

    public NodeDefinition(String name, String activeUpdatePort,
            String beginInLineComment, List<PortDeclaration> ports, String endInLineComment) {
        this.name = name;
        this.activeUpdatePort = activeUpdatePort;
        this.hasActiveUpdatePort = true;
        this.beginInLineComment = new Comment(Comment.Type.INLINE, beginInLineComment);
        this.ports = ports;
        this.endInLineComment = new Comment(Comment.Type.INLINE,endInLineComment);
    }
    
    

    public void setPorts(List<PortDeclaration> ports) {
        this.ports = ports;
    }

    public String getActiveUpdatePort() {
        return activeUpdatePort;
    }

    public Comment getBeginInLineComment() {
        return beginInLineComment;
    }

    public String getName() {
        return name;
    }

    public List<PortDeclaration> getPorts() {
        
        Map<PortType, List<PortDeclaration>> portTypes = ports.stream().collect(Collectors.groupingBy(p -> p.getType()));
        ports.clear();
        ports.addAll(sortAlpha(portTypes.get(PortType.Input)));
        ports.addAll(sortAlpha(portTypes.get(PortType.Evaluated)));
        ports.addAll(sortAlpha(portTypes.get(PortType.Output)));
        return ports;
    }
    
    private List<PortDeclaration> sortAlpha(List<PortDeclaration> ports){
        return ports.stream().sorted((p, s) -> p.getName().compareTo(s.getName())).collect(Collectors.toList());
    }

    public boolean hasActiveUpdatePort() {
        return hasActiveUpdatePort;
    }

    public Comment getEndInLineComment() {
        return endInLineComment;
    }
    
    @Override
    public String toCode() {
        STGroup group = new STGroupFile("shiro/definitions/shiro.stg");
        ST st = group.getInstanceOf("nodeDecl");
        st.add("n", this);
        return st.render();
    }
}
