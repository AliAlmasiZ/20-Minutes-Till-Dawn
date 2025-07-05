#ifdef GL_ES
    precision mediump float;
#endif

varying vec2 v_texCoords;
uniform sampler2D u_texture;

void main() {
    // Get the original color of the pixel from the texture
    vec4 color = texture2D(u_texture, v_texCoords);

    // Calculate the grayscale value using the standard luminosity method
    float gray = dot(color.rgb, vec3(0.299, 0.587, 0.114));

    // Set the final color to the grayscale value for R, G, and B
    gl_FragColor = vec4(gray, gray, gray, color.a);
}
