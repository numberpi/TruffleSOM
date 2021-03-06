package som.interpreter.nodes.nary;

import com.oracle.truffle.api.source.SourceSection;

import som.interpreter.nodes.ExpressionNode;
import som.interpreter.nodes.PreevaluatedExpression;
import som.vm.Universe;
import som.vmobjects.SSymbol;


/**
 * Common root class for node types that are eagerly specializable.
 * The main feature currently provided by this node is the implementation
 * of {@link PreevaluatedExpression}.
 *
 * <p>
 * The main use case at the moment is as common root for primitive nodes, which is used for
 * their specialization.
 */
public abstract class EagerlySpecializableNode extends ExpressionNode
    implements PreevaluatedExpression {

  protected EagerlySpecializableNode(final SourceSection source) {
    super(source);
  }

  /**
   * Create an eager primitive wrapper, which wraps this node.
   */
  public abstract EagerPrimitive wrapInEagerWrapper(SSymbol selector,
      ExpressionNode[] arguments, Universe universe);
}
