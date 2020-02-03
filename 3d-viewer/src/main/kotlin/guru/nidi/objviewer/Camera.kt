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

import javafx.scene.Group
import javafx.scene.PerspectiveCamera

fun Group.addCamera(camera: Camera) = children.add(camera.rotate)

class Camera {
    val camera = PerspectiveCamera(true).apply {
        nearClip = 0.1
        farClip = 10000.0
        translateZ = -450.0
    }
    val scale = Xform().apply {
        rz.angle = 180.0
        children.add(camera)
    }
    val translate = Xform().apply {
        children.add(scale)
    }
    val rotate = Xform().apply {
        ry.angle = 320.0
        rx.angle = 40.0
        children.add(translate)
    }
}
