import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}

// memo: サイレントじゃなくした
object SilentActor {
  def apply(): Behavior[Command] =
    Behaviors.setup(context => new SilentActor(context))

  sealed trait Command

  final case class SilentMessage(data: String, replyTo: ActorRef[Respond]) extends Command
  final case class Respond(data: Vector[String])
}

class SilentActor(context: ActorContext[SilentActor.Command])
  extends AbstractBehavior[SilentActor.Command](context) {
  import SilentActor._

  private var internalState = Vector[String]()

  override def onMessage(msg: Command): Behavior[Command] =
    msg match {
      case SilentMessage(data, replyTo) =>
        internalState = internalState :+ data
        replyTo ! Respond(internalState)
        this
    }
}