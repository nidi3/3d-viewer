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
import javafx.scene.paint.Color
import javafx.scene.paint.PhongMaterial
import javafx.scene.shape.CullFace
import javafx.scene.shape.DrawMode
import javafx.scene.shape.MeshView
import java.io.InputStream

fun readStl(input: InputStream): Node = MeshView().apply {
    drawMode = DrawMode.FILL
    material = PhongMaterial().apply {
        diffuseColor = Color.DARKGREEN
        specularColor = Color.GREEN
    }
    mesh = if (input.read() == 's'.toInt()) readStlAscii(input) else readStlBinary(input)
    cullFace = CullFace.NONE
}



