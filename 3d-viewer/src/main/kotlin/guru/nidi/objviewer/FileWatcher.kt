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

import java.io.File
import java.nio.file.*
import kotlin.concurrent.thread

class FileWatcher {
    private val watcher = FileSystems.getDefault().newWatchService()
    private var key: WatchKey? = null
    private var file: Path? = null
    private var notify: ((File) -> Unit) = {}

    init {
        thread(isDaemon = true) { watch() }
    }

    fun watch(file: File, notify: (File) -> Unit) {
        if (file.toPath().parent != this.file?.parent) {
            key?.cancel()
            key = file.toPath().parent.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY)
        }
        this.file = file.toPath()
        this.notify = notify
    }

    private fun watch() {
        while (true) {
            val key = watcher.take()
            for (event in key.pollEvents()) {
                file?.let { f ->
                    @Suppress("UNCHECKED_CAST")
                    val ev = event as WatchEvent<Path>
                    if (f.endsWith(ev.context())) notify(f.toFile())
                }
            }
            key.reset()
        }
    }
}
