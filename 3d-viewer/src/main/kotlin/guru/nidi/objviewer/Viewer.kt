/*
 * Copyright © 2020 Stefan Niederhauser (nidin@gmx.ch)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package guru.nidi.objviewer

import javafx.application.Application
import javafx.scene.*
import javafx.scene.control.Menu
import javafx.scene.control.MenuBar
import javafx.scene.control.MenuItem
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination.SHORTCUT_DOWN
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color.*
import javafx.scene.paint.PhongMaterial
import javafx.scene.shape.Box
import javafx.stage.Stage

class Viewer : Application() {
    private fun axes(): Node {
        val redMaterial = PhongMaterial().apply {
            diffuseColor = DARKRED
            specularColor = RED
        }
        val greenMaterial = PhongMaterial().apply {
            diffuseColor = DARKGREEN
            specularColor = GREEN
        }
        val blueMaterial = PhongMaterial().apply {
            diffuseColor = DARKBLUE
            specularColor = BLUE
        }
        return Group().apply {
            children.addAll(
                    Box(240.0, 1.0, 1.0).apply { material = redMaterial },
                    Box(1.0, 240.0, 1.0).apply { material = greenMaterial },
                    Box(1.0, 1.0, 240.0).apply { material = blueMaterial }
            )
        }
    }

    override fun start(stage: Stage) {
        val camera = Camera()
        val world = Group().apply {
            children.addAll(Group(), axes())
            addCamera(camera)
        }

        val loadDialog = LoadDialog(stage)
        val root = BorderPane().apply {
            top = MenuBar().apply {
                menus.add(Menu("File").apply {
                    items.add(MenuItem("Open...").apply {
                        accelerator = KeyCodeCombination(KeyCode.O, SHORTCUT_DOWN)
                        setOnAction {
                            loadDialog.show { model, file ->
                                stage.title = file.name
                                world.children[0] = model
                            }
                        }
                    })
                })
            }
            center = SubScene(world, 1024.0, 768.0, true, SceneAntialiasing.BALANCED).apply {
                this.camera = camera.camera
                fill = GREY
                handleMouse(camera)
            }
        }

        stage.apply {
            title = "3D Viewer"
            scene = Scene(root, 1024.0, 768.0, true)
            show()
        }
    }
}
