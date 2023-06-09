#version 330

layout (location = 0) in vec3 attrib_Position;
layout (location = 1) in vec4 attrib_Colour;
layout (location = 2) in vec2 attrib_TextureCoord;

out vec3 position;
out vec4 colour;
out vec2 textureCoord;

uniform mat4 u_ModelMatrix;
uniform mat4 u_ViewMatrix;
uniform mat4 u_ProjectionMatrix;

void main() {
    position = attrib_Position;
    colour = attrib_Colour;
    textureCoord = attrib_TextureCoord;
    gl_Position = u_ProjectionMatrix * u_ViewMatrix * u_ModelMatrix * vec4(attrib_Position, 1.0f);
}