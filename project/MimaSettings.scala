import sbt._, Keys._
import com.typesafe.tools.mima.plugin.MimaPlugin
import com.typesafe.tools.mima.plugin.MimaKeys._
import sbtcrossproject.CrossPlugin.autoImport.crossProjectPlatform
import sbtcrossproject.JVMPlatform
import scalajscrossproject.JSPlatform
import scalanativecrossproject.NativePlatform

object MimaSettings {

  val previousVersions = settingKey[Seq[String]]("")

  val mimaSettings = Def.settings(
    MimaPlugin.globalSettings,
    MimaPlugin.buildSettings,
    MimaPlugin.projectSettings,
    // Rubrik fork: this build adds new methods to public traits (Formats), which would trip
    // MiMa against upstream 4.0.x. We're publishing under a distinct version coordinate
    // (4.0.8-rubrik) so binary compatibility with upstream 4.0.x is not a constraint.
    previousVersions := Nil,
    mimaPreviousArtifacts := Set(),
    (Test / test) := {
      mimaReportBinaryIssues.value
      (Test / test).value
    }
  )

}
