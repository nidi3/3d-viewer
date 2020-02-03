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

import javafx.scene.shape.Mesh
import javafx.scene.shape.TriangleMesh
import java.io.InputStream

fun readStlBinary(input: InputStream): Mesh = input.use { inp ->
    val data = inp.readBytes()
    var pos = 79

    fun readInt() = (data[pos++].toInt() and 0xff shl 0) +
            (data[pos++].toInt() and 0xff shl 8) +
            (data[pos++].toInt() and 0xff shl 16) +
            (data[pos++].toInt() and 0xff shl 24)

    fun readFloat() = java.lang.Float.intBitsToFloat(readInt())

    val mesh = TriangleMesh()
    mesh.texCoords.addAll(0f, 0f)

    val count = readInt()
    for (i in 0 until count) {
        mesh.faces.addAll(i * 3, 0, i * 3 + 1, 0, i * 3 + 2, 0)
        pos += 12
        for (v in 1..3) {
            mesh.points.addAll(readFloat(), readFloat(), readFloat())
        }
        pos += 2
    }
    return mesh
}



