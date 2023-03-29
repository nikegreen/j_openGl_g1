#version 330

layout (location = 0) out vec4 out_Colour;

in vec3 position;
in vec4 colour;
in vec2 textureCoord;

uniform sampler2D u_TextureSampler;

void main() {
    out_Colour = texture(u_TextureSampler, textureCoord);
}