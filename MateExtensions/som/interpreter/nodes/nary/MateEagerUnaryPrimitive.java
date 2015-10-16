package som.interpreter.nodes.nary;

import som.interpreter.nodes.ExpressionNode;
import som.matenodes.MateAbstractReflectiveDispatch;
import som.matenodes.MateBehavior;
import som.matenodes.MateAbstractReflectiveDispatchNodeGen.MateDispatchMessageLookupNodeGen;
import som.matenodes.MateAbstractSemanticNodes.MateSemanticCheckNode;
import som.vm.MateSemanticsException;
import som.vmobjects.SSymbol;

import com.oracle.truffle.api.frame.VirtualFrame;


public class MateEagerUnaryPrimitive extends EagerUnaryPrimitiveNode implements MateBehavior {
  @Child MateSemanticCheckNode                   semanticCheck;
  @Child MateAbstractReflectiveDispatch     reflectiveDispatch;
  
  public MateEagerUnaryPrimitive(SSymbol selector, ExpressionNode receiver,
      UnaryExpressionNode primitive) {
    super(selector, receiver, primitive);
    semanticCheck = MateSemanticCheckNode.createForFullCheck(this.getSourceSection(), this.reflectiveOperation());
    reflectiveDispatch = MateDispatchMessageLookupNodeGen.create(this.getSourceSection());
  }

  @Override
  public Object executeGeneric(final VirtualFrame frame) {
    Object rcvr = this.getReceiver().executeGeneric(frame);
    try{
      return this.doMateSemantics(frame, new Object[] {rcvr});
    } catch(MateSemanticsException e){
      return executeEvaluated(frame, rcvr);
    }
  }
  
  @Override
  public MateSemanticCheckNode getMateNode() {
    return semanticCheck;
  }

  @Override
  public MateAbstractReflectiveDispatch getMateDispatch() {
    return reflectiveDispatch;
  }
}