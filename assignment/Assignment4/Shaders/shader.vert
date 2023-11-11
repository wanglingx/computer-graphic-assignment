#version 330

layout (location = 0) in vec3 pos; //xyx
layout (location = 1) in vec2 aTexCoord; //xy
layout (location = 2) in vec3 aNormal;

out vec4 vCol;
out vec2 TexCoord;
out vec3 Normal;

out vec3 FragPos;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main()
{
    gl_Position = projection * view * model * vec4(pos,1.0f);
    FragPos = vec3(model * vec4(pos,1.0));
    vCol = vec4(clamp(pos, 0.0f, 1.0f),1.0f);
    TexCoord = aTexCoord;
    Normal = aNormal;
}