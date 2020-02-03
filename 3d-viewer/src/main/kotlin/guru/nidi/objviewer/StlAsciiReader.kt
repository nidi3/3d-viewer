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

fun readStlAscii(input: InputStream): Mesh = input.reader().useLines { lines ->
    val mesh = TriangleMesh()
    mesh.texCoords.addAll(0f, 0f)
    var points = 0
    val iter = lines.filter { it.isNotBlank() }.map { it.split(Regex("""\s+""")) }.iterator()

    fun expect(msg: String, pred: (List<String>) -> Boolean) = iter.next().also {
        if (!pred(it)) throw IllegalArgumentException("$msg expected, but found '${it.joinToString(" ")}'")
    }

    fun expect(s: String) = expect(s) { it.firstOrNull() == s }

    expect("olid")
    while (true) {
        val facet = expect("facet or endsolid") {
            it.firstOrNull() in listOf("endsolid", "facet")
        }
        if (facet.first() == "endsolid") break
        expect("outer loop") {
            it.firstOrNull() == "outer" && it.getOrNull(1) == "loop"
        }
        mesh.faces.addAll(points, 0, points + 1, 0, points + 2, 0)
        for (i in 1..3) {
            val v = expect("vertex with 3 values") {
                it.firstOrNull() == "vertex" && it.size == 4
            }
            mesh.points.addAll(*v.takeLast(3).map { it.toFloat() }.toFloatArray())
            points++
        }
        expect("endloop")
        expect("endfacet")
    }
    mesh
}
