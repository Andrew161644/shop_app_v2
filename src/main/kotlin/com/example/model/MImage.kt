package com.example.model

import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import javax.imageio.ImageIO

class MImage(var filename: String, var image: String, var type: String) {
    companion object Builder{
        fun build(file: File):MImage{
            val originalImage = ImageIO.read(file)
            val baos = ByteArrayOutputStream()
            ImageIO.write(originalImage, file.extension, baos)

            /*data:image/jpeg;charset=utf-8;base64, */
            val s = "data:image/"+file.extension + ";base64," + Base64.getEncoder().encodeToString(baos.toByteArray())
            baos.flush()
            return MImage(file.name, s, file.extension)
        }

    }
}
