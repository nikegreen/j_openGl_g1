#version 330

layout (location = 0) in vec3 attrib_Position;

void main() {
    gl_Position = vec4(attrib_Position, 1.0f);
}