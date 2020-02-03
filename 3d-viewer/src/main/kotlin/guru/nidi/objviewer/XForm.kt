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
import javafx.scene.transform.Rotate
import javafx.scene.transform.Rotate.*
import javafx.scene.transform.Scale
import javafx.scene.transform.Translate

class Xform : Group() {
    var t = Translate()
    var rx = Rotate().apply { axis = X_AXIS }
    var ry = Rotate().apply { axis = Y_AXIS }
    var rz = Rotate().apply { axis = Z_AXIS }
    var s = Scale()

    init {
        transforms.addAll(t, rz, ry, rx, s)
    }
}
