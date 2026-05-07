import com.intellij.driver.sdk.invokeAction
import com.intellij.driver.sdk.ui.components.UiComponent.Companion.waitFound
import com.intellij.driver.sdk.ui.components.common.ideFrame
import com.intellij.driver.sdk.ui.components.elements.button
import com.intellij.driver.sdk.ui.components.elements.checkBox
import com.intellij.driver.sdk.ui.components.settings.settingsDialog
import com.intellij.driver.sdk.ui.xQuery
import com.intellij.ide.starter.config.ConfigurationStorage
import com.intellij.ide.starter.config.splitMode
import com.intellij.ide.starter.driver.engine.runIdeWithDriver
import com.intellij.ide.starter.models.IdeInfo
import com.intellij.ide.starter.models.TestCase
import com.intellij.ide.starter.project.GitHubProject
import com.intellij.ide.starter.runner.CurrentTestMethod
import com.intellij.ide.starter.runner.Starter
import com.intellij.ide.starter.junit5.hyphenateWithClass
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class UiTestWithDriverCheckbox {

    @Test
    fun testChangelistCheckbox() {

        ConfigurationStorage.splitMode(false)

        val testContext = Starter
            .newContext(
                CurrentTestMethod.hyphenateWithClass(),
                TestCase(
                    IdeInfo(
                        productCode = "IU",
                        platformPrefix = "Idea",
                        executableFileName = "idea",
                        fullName = "IntelliJ IDEA Ultimate"
                    ),
                    GitHubProject.fromGithub(
                        branchName = "main",
                        repoRelativeUrl = "JetBrains/intellij-samples"
                    )
                )
            )
            .prepareProjectCleanImport()

        testContext.runIdeWithDriver().useDriverAndCloseIde {

            ideFrame {

                // 1. Open Settings (NEVER search dialog manually)
                driver.invokeAction("ShowSettings", now = true)

                // 2. Use Settings DSL (this is the correct entry point)
                val settings = settingsDialog()
                settings.waitFound()

                // 3. LEFT TREE: click Version Control
                settings.settingsTree.waitFound().clickPath("Version Control")

                // 4. RIGHT PANEL: click Changelists
                settings.settingsTree.clickPath("Version Control", "Changelists")

                // 5. Find checkbox and toggle it
                val checkbox = settings.checkBox(
                    xQuery {
                        byAccessibleName("Create changelists automatically")
                    }
                )

                checkbox.waitFound()
                checkbox.click()

                // 6. Assertion
                assertTrue(checkbox.isSelected()) {
                    "Checkbox was not selected"
                }

                // 7. Click OK
                settings.button("OK").click()
            }
        }
    }
}