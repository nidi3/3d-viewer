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

import javafx.scene.Node
import kotlin.math.exp

fun Node.handleMouse(camera: Camera) {
    MouseHandler().handle(this, camera)
}

class MouseHandler {
    var mousePosX = 0.0
    var mousePosY = 0.0
    var mouseOldX = 0.0
    var mouseOldY = 0.0
    var mouseDeltaX = 0.0
    var mouseDeltaY = 0.0

    fun handle(node: Node, camera: Camera) {
        node.setOnMousePressed { me ->
            mousePosX = me.sceneX
            mousePosY = me.sceneY
            mouseOldX = me.sceneX
            mouseOldY = me.sceneY
        }
        node.setOnScroll { me ->
            camera.camera.translateZ *= exp(-me.deltaY / 100)
        }
        node.setOnMouseDragged { me ->
            mouseOldX = mousePosX
            mouseOldY = mousePosY
            mousePosX = me.sceneX
            mousePosY = me.sceneY
            mouseDeltaX = mousePosX - mouseOldX
            mouseDeltaY = mousePosY - mouseOldY
            if (me.isPrimaryButtonDown) {
                when {
                    me.isShiftDown -> {
                        camera.translate.t.x += mouseDeltaX
                        camera.translate.t.y += mouseDeltaY
                    }
                    else -> {
                        camera.rotate.ry.angle += -mouseDeltaX
                        camera.rotate.rx.angle += mouseDeltaY
                    }
                }
            }
        }
    }
}
