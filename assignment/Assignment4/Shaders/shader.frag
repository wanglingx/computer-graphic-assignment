#version 330

in vec4 vCol;
in vec2 TexCoord;
in vec3 FragPos;
in vec3 Normal;
out vec4 colour;

uniform vec3 lightColour;
uniform vec3 lightPos;

uniform vec3 viewPos;
uniform sampler2D texture2D;

vec3 ambientLight()
{
    float   ambientStrength = 0.35f;
    vec3    ambient = ambientStrength * lightColour;
    return  ambient;
}

vec3 diffuseLight()
{
    float diffuseStrength = 0.5;

    vec3 norm = normalize(Normal);
    vec3 lightDir = normalize(lightPos - FragPos);

    float diff = max(dot(norm,lightDir),0.0);
    vec3 diffuse = lightColour * diff * diffuseStrength;

    return diffuse;
}

vec3 specularLight()
{
    float specularStrength = 0.8;
    float shininess = 64.0;
    vec3 norm = normalize(Normal);
    vec3 lightDir = normalize(lightPos - FragPos);

    vec3 reflecDir = reflect(-lightDir,norm);
    vec3 viewDir = normalize(viewPos - FragPos);

    //Phong reflectance model
    float spec = pow(max(dot(viewDir, reflecDir),0.0),shininess);

    //Blinn-Phong reflectance model
    //vec3 halfDir = (lightDir * viewDir) / 2.0;
    //float spec = pow(max(dot(norm, halfDir),0.0),shininess);

    vec3 specular = lightColour * spec * specularStrength;

    return specular;
}

void main()
{
    //phong shading
    colour = texture(texture2D, TexCoord) * vec4(ambientLight() + diffuseLight() + specularLight() , 1.0);
}