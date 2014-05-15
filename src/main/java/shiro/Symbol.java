package shiro;

/**
 * Defines a symbol in Shiro
 * Allows a node, a port, or a subjunctive node to be handled as a common type
 * of object.
 * @author jeffreyguenther
 */
public interface Symbol {
    public SymbolType getSymbolType();
    public void activate();
    public void deactivate();
    public void setFullName(String  fullName);
    public String getFullName();
    public void setName(String name);
    public String getName();
}
