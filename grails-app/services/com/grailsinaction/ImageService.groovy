package com.grailsinaction

import grails.transaction.Transactional

import org.codehaus.groovy.grails.core.io.ResourceLocator

import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.awt.Graphics2D
import java.awt.geom.AffineTransform
import java.io.ByteArrayInputStream

@Transactional
class ImageService {

    ResourceLocator grailsResourceLocator

    /**
     * Scales an image supplied as a byte array. The width and
     * height of the returned image is determined by the other
     * parameters.
     */
    byte[] scale(byte[] source, int width, int height) throws IOException {
        BufferedImage original = ImageIO.read(new ByteArrayInputStream(source))
        BufferedImage scaled = new BufferedImage(width, height, 
                BufferedImage.TYPE_INT_RGB)
        Graphics2D graphics = scaled.createGraphics()
        AffineTransform transform = AffineTransform.getScaleInstance(
                (double) width / original.getWidth(),
                (double) height / original.getHeight())
        graphics.drawRenderedImage(original, transform)
        ByteArrayOutputStream out = new ByteArrayOutputStream()
        ImageIO.write(scaled, "PNG", out)
        return out.toByteArray()
    }

    /**
     * Returns the profile photo of a given user if one exists,
     * returning a default image if it does not.
     */
    byte[] profileImageForUser(String loginId) {
        def user = User.findByLoginId(loginId)
        def image = user.profile.photo 
        if (!image) {
            log.debug "No profile photo found, using default image"
            def imageResource = grailsResourceLocator
                    .findResourceForURI('/images/user-default-image.png')
            image = imageResource.inputStream.getBytes()
        }
        return image
    }

    /**
     * Returns a profile photo for a given user, scaled to 60 pixels square.
     */
    byte[] getThumbnail(String loginId) {
        scale(profileImageForUser(loginId), 60, 60)
    }
            
    /**
     * Returns a profile photo for a given user, scaled to 24 pixels square.
     */
    byte[] getTinyThumbnail(String loginId) {
        scale(profileImageForUser(loginId), 24, 24)
    }
}
