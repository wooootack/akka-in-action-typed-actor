import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import org.scalatest.wordspec.AnyWordSpecLike

class SilentActorSpec
  extends ScalaTestWithActorTestKit with AnyWordSpecLike  {
  import SilentActor._

  "Silent Actor" must {
    "メッセージを受信すると、内部状態が変化する" in {
      val probe = createTestProbe[Respond]()
      val silentActor = spawn(SilentActor())
      silentActor ! SilentMessage("whisper", probe.ref)
      val response = probe.receiveMessage()
      response.data should contain("whisper")
    }
  }
}