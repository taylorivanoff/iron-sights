#version 410 core
out vec4 FragColor;

in vec3 FragPos;
in vec3 Normal;
in vec2 TexCoord;

void main() {
    // Simple lighting calculation
    vec3 lightDir = normalize(vec3(1.0, 1.0, 1.0));
    float diff = max(dot(Normal, lightDir), 0.0);
    vec3 diffuse = diff * vec3(1.0, 1.0, 1.0);

    FragColor = vec4(diffuse, 1.0);
}