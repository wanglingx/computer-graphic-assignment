#undef GLFW_DLL
#include <iostream>
#include <stdio.h>
#include <string>
#include <string.h>

#include <GL/glew.h>
#include <GLFW/glfw3.h>

#include <vector>
#include <cmath>

#include "Libs/Shader.h"
#include "Libs/Window.h"
#include "Libs/Mesh.h"
#include "Libs/stb_image.h"

#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>

const GLint WIDTH = 800, HEIGHT = 600;

Window mainWindow;
std::vector<Mesh *> meshList;
std::vector<Shader *> shaderList;
Mesh *light;
Mesh *windowL;
Mesh *windowM;
glm::vec3 lightColour = glm::vec3(0.99f, 0.96f, 0.86f);
glm::vec3 lightPos = glm::vec3(5.0f, 5.0f, 0.0f);

static const char *vShader = "Shaders/shader.vert";
static const char *fShader = "Shaders/shader.frag";
static const char *lightVShader = "Shaders/lightShader.vert";
static const char *lightFShader = "Shaders/lightShader.frag";

float yaw = 0.0f;
float pitch = 0.0f;

void CreateOBJ()
{
    Mesh *obj1 = new Mesh();
    bool loaded = obj1->CreateMeshFromOBJ("Models/ground.obj");
    if (loaded)
    {
        meshList.push_back(obj1);
    }
    else
    {
        std::cout << "Failed to load model" << std::endl;
    }

    Mesh *obj2 = new Mesh();
    loaded = obj2->CreateMeshFromOBJ("Models/cube.obj");
    if (loaded)
    {
        meshList.push_back(obj2);
    }
    else
    {
        std::cout << "Failed to load model" << std::endl;
    }

    Mesh *obj3 = new Mesh();
    loaded = obj3->CreateMeshFromOBJ("Models/Rectangle.obj");
    if (loaded)
    {
        meshList.push_back(obj3);
    }
    else
    {
        std::cout << "Failed to load model" << std::endl;
    }

    Mesh *obj4 = new Mesh();
    loaded = obj4->CreateMeshFromOBJ("Models/raw.obj");
    if (loaded)
    {
        meshList.push_back(obj4);
    }
    else
    {
        std::cout << "Failed to load model" << std::endl;
    }

    Mesh *obj5 = new Mesh();
    loaded = obj5->CreateMeshFromOBJ("Models/Gate1.obj");
    if (loaded)
    {
        meshList.push_back(obj5);
    }
    else
    {
        std::cout << "Failed to load model" << std::endl;
    }

    Mesh *obj6 = new Mesh();
    loaded = obj6->CreateMeshFromOBJ("Models/balc1.obj");
    if (loaded)
    {
        meshList.push_back(obj6);
    }
    else
    {
        std::cout << "Failed to load model" << std::endl;
    }

    Mesh *obj7 = new Mesh();
    loaded = obj7->CreateMeshFromOBJ("Models/balc2.obj");
    if (loaded)
    {
        meshList.push_back(obj7);
    }
    else
    {
        std::cout << "Failed to load model" << std::endl;
    }

    Mesh *obj8 = new Mesh();
    loaded = obj8->CreateMeshFromOBJ("Models/window.obj");
    if (loaded)
    {
        meshList.push_back(obj8);
    }
    else
    {
        std::cout << "Failed to load model" << std::endl;
    }

    Mesh *obj9 = new Mesh();
    loaded = obj9->CreateMeshFromOBJ("Models/window1.obj");
    if (loaded)
    {
        meshList.push_back(obj9);
    }
    else
    {
        std::cout << "Failed to load model" << std::endl;
    }

    Mesh *obj10 = new Mesh();
    loaded = obj10->CreateMeshFromOBJ("Models/beach.obj");
    if (loaded)
    {
        meshList.push_back(obj10);
    }
    else
    {
        std::cout << "Failed to load model" << std::endl;
    }

    Mesh *obj11 = new Mesh();
    loaded = obj11->CreateMeshFromOBJ("Models/slide.obj");
    if (loaded)
    {
        meshList.push_back(obj11);
    }
    else
    {
        std::cout << "Failed to load model" << std::endl;
    }

    light = new Mesh();
    loaded = light->CreateMeshFromOBJ("Models/O.obj");
    if (!loaded)
    {
        std::cout << "Failed to load model" << std::endl;
        delete (light);
    }

    windowL = new Mesh();
    loaded = windowL->CreateMeshFromOBJ("Models/window.obj");
    if (!loaded)
    {
        std::cout << "Failed to load model" << std::endl;
        delete (windowL);
    }

    windowM = new Mesh();
    loaded = windowM->CreateMeshFromOBJ("Models/window1.obj");
    if (!loaded)
    {
        std::cout << "Failed to load model" << std::endl;
        delete (windowM);
    }
}

void CreateShaders()
{
    Shader *shader1 = new Shader();
    shader1->CreateFromFiles(vShader, fShader);
    shaderList.push_back(shader1);

    Shader *shader2 = new Shader();
    shader2->CreateFromFiles(lightVShader, lightFShader);
    shaderList.push_back(shader2);
}

void mouse_callback(GLFWwindow *window, double xPos, double yPos)
{
    static float lastX = mainWindow.getBufferWidth() / 2.0f;
    static float lastY = mainWindow.getBufferHeight() / 2.0f;

    float xOffset = xPos - lastX;
    float yOffset = lastY - yPos;

    lastX = xPos;
    lastY = yPos;

    float sensitivity = 0.1f;

    xOffset *= sensitivity;
    yOffset *= sensitivity;

    yaw += xOffset;
    pitch += yOffset;

    if (pitch > 89.0f)
        pitch = 89.0f;
    if (pitch < -89.0f)
        pitch = -89.0f;
}

int main()
{
    mainWindow = Window(WIDTH, HEIGHT, 3, 3);
    mainWindow.initialise();
    CreateOBJ();
    CreateShaders();

    GLuint uniformModel = 0, uniformProjection = 0, uniformView = 0;
    glm::mat4 projection = glm::perspective(45.0f, (GLfloat)mainWindow.getBufferWidth() / (GLfloat)mainWindow.getBufferHeight(), 0.1f, 100.0f);
    glm::vec3 cameraPos = glm::vec3(10.0f, 10.0f, 40.0f);
    glm::vec3 cameraTarget = glm::vec3(0.0f, 1.0f, -1.0f);
    glm::vec3 up = glm::vec3(0.0f, 1.0f, 0.0f);

    glm::vec3 cameraDirection = glm::normalize(cameraTarget - cameraPos);
    glm::vec3 cameraRight = glm::normalize(glm::cross(cameraDirection, up));
    glm::vec3 cameraUp = glm::normalize(glm::cross(cameraDirection, -cameraRight));

    glfwSetCursorPosCallback(mainWindow.getWindow(), mouse_callback);

    // texture
    unsigned int texture;
    glGenTextures(1, &texture);
    glBindTexture(GL_TEXTURE_2D, texture);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

    // grass Testure
    int width, height, nrChannels;
    unsigned char *data = stbi_load("Textures/uvmap.png", &width, &height, &nrChannels, 0);
    if (data)
    {
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
        glGenerateMipmap(GL_TEXTURE_2D);
    }
    else
    {
        std::cout << "Failed to load texture" << std::endl;
    }
    stbi_image_free(data);

    unsigned int water;
    glGenTextures(1, &water);
    glBindTexture(GL_TEXTURE_2D, water);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

    int width1, height1, nrChannels1;
    unsigned char *data1 = stbi_load("Textures/water3.jpg", &width1, &height1, &nrChannels1, 0);
    if (data1)
    {
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width1, height1, 0, GL_RGB, GL_UNSIGNED_BYTE, data1);
        glGenerateMipmap(GL_TEXTURE_2D);
    }
    else
    {
        std::cout << "Failed to load texture" << std::endl;
    }
    stbi_image_free(data1);

    unsigned int plant;
    glGenTextures(1, &plant);
    glBindTexture(GL_TEXTURE_2D, plant);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

    int width2, height2, nrChannels2;
    unsigned char *data2 = stbi_load("Textures/grass1.jpg", &width2, &height2, &nrChannels2, 0);
    if (data2)
    {
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width2, height2, 0, GL_RGB, GL_UNSIGNED_BYTE, data2);
        glGenerateMipmap(GL_TEXTURE_2D);
    }
    else
    {
        std::cout << "Failed to load texture" << std::endl;
    }
    stbi_image_free(data2);

    unsigned int wall;
    glGenTextures(1, &wall);
    glBindTexture(GL_TEXTURE_2D, wall);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

    int width3, height3, nrChannels3;
    unsigned char *data3 = stbi_load("Textures/wall.jpg", &width3, &height3, &nrChannels3, 0);
    if (data3)
    {
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width3, height, 0, GL_RGB, GL_UNSIGNED_BYTE, data3);
        glGenerateMipmap(GL_TEXTURE_2D);
    }
    else
    {
        std::cout << "Failed to load texture" << std::endl;
    }
    stbi_image_free(data3);

    unsigned int gray;
    glGenTextures(1, &gray);
    glBindTexture(GL_TEXTURE_2D, gray);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

    int width4, height4, nrChannels4;
    unsigned char *data4 = stbi_load("Textures/gray1.jpg", &width4, &height4, &nrChannels4, 0);
    if (data4)
    {
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width4, height4, 0, GL_RGB, GL_UNSIGNED_BYTE, data4);
        glGenerateMipmap(GL_TEXTURE_2D);
    }
    else
    {
        std::cout << "Failed to load texture" << std::endl;
    }
    stbi_image_free(data4);

    unsigned int solid;
    glGenTextures(1, &solid);
    glBindTexture(GL_TEXTURE_2D, solid);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

    int width5, height5, nrChannels5;
    unsigned char *data5 = stbi_load("Textures/solid.jpg", &width5, &height5, &nrChannels5, 0);
    if (data5)
    {
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width5, height5, 0, GL_RGB, GL_UNSIGNED_BYTE, data5);
        glGenerateMipmap(GL_TEXTURE_2D);
    }
    else
    {
        std::cout << "Failed to load texture" << std::endl;
    }
    stbi_image_free(data5);

    unsigned int white;
    glGenTextures(1, &white);
    glBindTexture(GL_TEXTURE_2D, white);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

    int width6, height6, nrChannels6;
    unsigned char *data6 = stbi_load("Textures/pink.jpg", &width6, &height6, &nrChannels6, 0);
    if (data6)
    {
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width6, height6, 0, GL_RGB, GL_UNSIGNED_BYTE, data6);
        glGenerateMipmap(GL_TEXTURE_2D);
    }
    else
    {
        std::cout << "Failed to load texture" << std::endl;
    }
    stbi_image_free(data6);

    unsigned int slider;
    glGenTextures(1, &slider);
    glBindTexture(GL_TEXTURE_2D, slider);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

    int width7, height7, nrChannels7;
    unsigned char *data7 = stbi_load("Textures/red.jpg", &width7, &height7, &nrChannels7, 0);
    if (data7)
    {
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width7, height7, 0, GL_RGB, GL_UNSIGNED_BYTE, data7);
        glGenerateMipmap(GL_TEXTURE_2D);
    }
    else
    {
        std::cout << "Failed to load texture" << std::endl;
    }
    stbi_image_free(data7);

    float deltaTime;
    float lastFrame = glfwGetTime();

    while (!mainWindow.getShouldClose())
    {
        // Get + Handle user input events
        glfwPollEvents();

        float currentFrame = glfwGetTime();
        deltaTime = currentFrame - lastFrame;
        lastFrame = currentFrame;

        lightPos.x = 1.0f + sin(glfwGetTime() * 2.0f);
        lightPos.y = sin(glfwGetTime() / 2.0f);
        lightPos.z = 0.0f;

        glm::vec3 cameraDirection = glm::normalize(cameraTarget - cameraPos);
        glm::vec3 cameraRight = glm::normalize(glm::cross(cameraDirection, up));
        glm::vec3 cameraUp = glm::normalize(glm::cross(cameraDirection, -cameraRight));

        if (glfwGetKey(mainWindow.getWindow(), GLFW_KEY_W) == GLFW_PRESS)
            cameraPos += cameraDirection * 0.01f;
        if (glfwGetKey(mainWindow.getWindow(), GLFW_KEY_S) == GLFW_PRESS)
            cameraPos -= cameraDirection * 0.01f;
        if (glfwGetKey(mainWindow.getWindow(), GLFW_KEY_A) == GLFW_PRESS)
            cameraPos -= cameraRight * 0.01f;
        if (glfwGetKey(mainWindow.getWindow(), GLFW_KEY_D) == GLFW_PRESS)
            cameraPos += cameraRight * 0.01f;
        if (glfwGetKey(mainWindow.getWindow(), GLFW_KEY_SPACE) == GLFW_PRESS)
            cameraPos += cameraUp * 0.01f;
        else if (glfwGetKey(mainWindow.getWindow(), GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS)
            cameraPos -= cameraUp * 0.01f;

        // Clear window
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        // draw here
        shaderList[0]->UseShader();
        uniformModel = shaderList[0]->GetUniformLocation("model");
        uniformView = shaderList[0]->GetUniformLocation("view");
        uniformProjection = shaderList[0]->GetUniformLocation("projection");

        glm::mat4 view(1.0f);
        glm::mat4 cameraPosMat(1.0f);
        cameraPosMat[0][3] = -cameraPos.x;
        cameraPosMat[1][3] = -cameraPos.y;
        cameraPosMat[2][3] = -cameraPos.z;

        glm::mat4 cameraRotateMat(1.0f);
        cameraRotateMat[0] = glm::vec4(cameraRight.x, cameraUp.x, -cameraDirection.x, 0.0f);
        cameraRotateMat[1] = glm::vec4(cameraRight.y, cameraUp.y, -cameraDirection.y, 0.0f);
        cameraRotateMat[2] = glm::vec4(cameraRight.z, cameraUp.z, -cameraDirection.z, 0.0f);

        view = glm::lookAt(cameraPos, cameraTarget, up);

        // Object ground
        glm::mat4 model(1.0f);
        model = glm::translate(model, glm::vec3(1.0f, 1.0f, 1.0f));
        model = glm::scale(model, glm::vec3(0.9f, 0.9f, 1.0f));

        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));

        glUniform3fv(shaderList[0]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        glUniform3fv(shaderList[0]->GetUniformLocation("lightPos"), 1, (GLfloat *)&lightPos);
        glUniform3fv(shaderList[0]->GetUniformLocation("viewPos"), 1, (GLfloat *)&cameraPos);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, plant);
        meshList[0]->RenderMesh();

        // box house1
        glm::mat4 model1(1.0f);
        model1 = glm::translate(model1, glm::vec3(10.0f, 6.0f, 10.0f));
        model1 = glm::scale(model1, glm::vec3(9.0f, 4.0f, 12.0f));

        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model1));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));

        glUniform3fv(shaderList[0]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        glUniform3fv(shaderList[0]->GetUniformLocation("lightPos"), 1, (GLfloat *)&lightPos);
        glUniform3fv(shaderList[0]->GetUniformLocation("viewPos"), 1, (GLfloat *)&cameraPos);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, wall);
        meshList[1]->RenderMesh();

        // หลังคา
        glm::mat4 model2(1.0f);
        model2 = glm::translate(model2, glm::vec3(10.0f, -0.1f, 10.0f));
        model2 = glm::scale(model2, glm::vec3(1.0f, 0.55f, 0.7f));

        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model2));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));

        glUniform3fv(shaderList[0]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        glUniform3fv(shaderList[0]->GetUniformLocation("lightPos"), 1, (GLfloat *)&lightPos);
        glUniform3fv(shaderList[0]->GetUniformLocation("viewPos"), 1, (GLfloat *)&cameraPos);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, gray);
        meshList[2]->RenderMesh();

        // HOUSRbox2
        glm::mat4 model3(1.0f);
        model3 = glm::translate(model3, glm::vec3(10.0f, 14.0f, 14.5f));
        model3 = glm::scale(model3, glm::vec3(9.0f, 4.0f, 7.0f));

        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model3));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));

        glUniform3fv(shaderList[0]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        glUniform3fv(shaderList[0]->GetUniformLocation("lightPos"), 1, (GLfloat *)&lightPos);
        glUniform3fv(shaderList[0]->GetUniformLocation("viewPos"), 1, (GLfloat *)&cameraPos);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, wall);
        meshList[1]->RenderMesh();

        // fence 1.1
        glm::mat4 model4(1.0f);
        model4 = glm::translate(model4, glm::vec3(11.0f, 2.0f, 25.5f));
        model4 = glm::scale(model4, glm::vec3(0.05f, 0.05f, 0.1f));

        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model4));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));

        glUniform3fv(shaderList[0]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        glUniform3fv(shaderList[0]->GetUniformLocation("lightPos"), 1, (GLfloat *)&lightPos);
        glUniform3fv(shaderList[0]->GetUniformLocation("viewPos"), 1, (GLfloat *)&cameraPos);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, solid);
        meshList[3]->RenderMesh();

        // fence 1.2
        glm::mat4 model5(1.0f);
        model5 = glm::translate(model5, glm::vec3(-8.5f, 2.0f, 25.5f));
        model5 = glm::scale(model5, glm::vec3(0.05f, 0.05f, 0.1f));

        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model5));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));

        glUniform3fv(shaderList[0]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        glUniform3fv(shaderList[0]->GetUniformLocation("lightPos"), 1, (GLfloat *)&lightPos);
        glUniform3fv(shaderList[0]->GetUniformLocation("viewPos"), 1, (GLfloat *)&cameraPos);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, solid);
        meshList[3]->RenderMesh();

        // fence 2.1
        glm::mat4 model6(1.0f);
        model6 = glm::translate(model6, glm::vec3(11.0f, 2.0f, -23.5f));
        model6 = glm::scale(model6, glm::vec3(0.05f, 0.05f, 0.1f));

        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model6));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));

        glUniform3fv(shaderList[0]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        glUniform3fv(shaderList[0]->GetUniformLocation("lightPos"), 1, (GLfloat *)&lightPos);
        glUniform3fv(shaderList[0]->GetUniformLocation("viewPos"), 1, (GLfloat *)&cameraPos);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, solid);
        meshList[3]->RenderMesh();

        // fence 2.2
        glm::mat4 model7(1.0f);
        model7 = glm::translate(model7, glm::vec3(-8.5f, 2.0f, -23.5f));
        model7 = glm::scale(model7, glm::vec3(0.05f, 0.05f, 0.1f));

        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model7));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));

        glUniform3fv(shaderList[0]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        glUniform3fv(shaderList[0]->GetUniformLocation("lightPos"), 1, (GLfloat *)&lightPos);
        glUniform3fv(shaderList[0]->GetUniformLocation("viewPos"), 1, (GLfloat *)&cameraPos);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, solid);
        meshList[3]->RenderMesh();

        // gate
        glm::mat4 model8(1.0f);
        model8 = glm::translate(model8, glm::vec3(22.0f, 1.5f, -14.0f));
        model8 = glm::scale(model8, glm::vec3(0.96f, 0.96f, 0.96f));

        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model8));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));

        glUniform3fv(shaderList[0]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        glUniform3fv(shaderList[0]->GetUniformLocation("lightPos"), 1, (GLfloat *)&lightPos);
        glUniform3fv(shaderList[0]->GetUniformLocation("viewPos"), 1, (GLfloat *)&cameraPos);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, solid);
        meshList[4]->RenderMesh();

        // poolup
        glm::mat4 model9(1.0f);
        model9 = glm::translate(model9, glm::vec3(8.52f, 9.99f, -0.55f));
        model9 = glm::scale(model9, glm::vec3(0.45f, 1.0f, 0.25f));

        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model9));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));

        glUniform3fv(shaderList[0]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        glUniform3fv(shaderList[0]->GetUniformLocation("lightPos"), 1, (GLfloat *)&lightPos);
        glUniform3fv(shaderList[0]->GetUniformLocation("viewPos"), 1, (GLfloat *)&cameraPos);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, water);
        meshList[0]->RenderMesh();

        // pool down
        glm::mat4 model10(1.0f);
        model10 = glm::translate(model10, glm::vec3(-13.5f, 2.0f, 1.0f));
        model10 = glm::scale(model10, glm::vec3(0.3f, 1.0f, 0.85f));

        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model10));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));

        glUniform3fv(shaderList[0]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        glUniform3fv(shaderList[0]->GetUniformLocation("lightPos"), 1, (GLfloat *)&lightPos);
        glUniform3fv(shaderList[0]->GetUniformLocation("viewPos"), 1, (GLfloat *)&cameraPos);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, water);
        meshList[0]->RenderMesh();

        // fence up1
        glm::mat4 model11(1.0f);
        model11 = glm::translate(model11, glm::vec3(11.5f, 18.0f, 21.0f));
        model11 = glm::scale(model11, glm::vec3(0.17f, 0.2f, 0.2f));

        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model11));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));

        glUniform3fv(shaderList[0]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        glUniform3fv(shaderList[0]->GetUniformLocation("lightPos"), 1, (GLfloat *)&lightPos);
        glUniform3fv(shaderList[0]->GetUniformLocation("viewPos"), 1, (GLfloat *)&cameraPos);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, gray);
        meshList[5]->RenderMesh();

        // fence up3
        glm::mat4 model12(1.0f);
        model12 = glm::translate(model12, glm::vec3(1.5f, 18.0f, 16.0f));
        model12 = glm::scale(model12, glm::vec3(0.22f, 0.2f, 0.13f));

        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model12));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));

        glUniform3fv(shaderList[0]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        glUniform3fv(shaderList[0]->GetUniformLocation("lightPos"), 1, (GLfloat *)&lightPos);
        glUniform3fv(shaderList[0]->GetUniformLocation("viewPos"), 1, (GLfloat *)&cameraPos);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, gray);
        meshList[6]->RenderMesh();

        // fence up2
        glm::mat4 model13(1.0f);
        model13 = glm::translate(model13, glm::vec3(11.5f, 18.0f, 8.5f));
        model13 = glm::scale(model13, glm::vec3(0.17f, 0.2f, 0.2f));

        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model13));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));

        glUniform3fv(shaderList[0]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        glUniform3fv(shaderList[0]->GetUniformLocation("lightPos"), 1, (GLfloat *)&lightPos);
        glUniform3fv(shaderList[0]->GetUniformLocation("viewPos"), 1, (GLfloat *)&cameraPos);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, gray);
        meshList[5]->RenderMesh();

        // fence up4
        glm::mat4 model14(1.0f);
        model14 = glm::translate(model14, glm::vec3(18.0f, 18.0f, 16.0f));
        model14 = glm::scale(model14, glm::vec3(0.22f, 0.2f, 0.13f));

        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model14));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));

        glUniform3fv(shaderList[0]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        glUniform3fv(shaderList[0]->GetUniformLocation("lightPos"), 1, (GLfloat *)&lightPos);
        glUniform3fv(shaderList[0]->GetUniformLocation("viewPos"), 1, (GLfloat *)&cameraPos);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, gray);
        meshList[6]->RenderMesh();

        // box door 1
        glm::mat4 model15(1.0f);
        model15 = glm::translate(model15, glm::vec3(22.5f, 4.0f, -22.0f));
        model15 = glm::scale(model15, glm::vec3(1.0f, 2.0f, 1.5f));

        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model15));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));

        glUniform3fv(shaderList[0]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        glUniform3fv(shaderList[0]->GetUniformLocation("lightPos"), 1, (GLfloat *)&lightPos);
        glUniform3fv(shaderList[0]->GetUniformLocation("viewPos"), 1, (GLfloat *)&cameraPos);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, plant);
        meshList[1]->RenderMesh();

        // box door 2
        glm::mat4 model16(1.0f);
        model16 = glm::translate(model16, glm::vec3(22.5f, 4.0f, 9.1f));
        model16 = glm::scale(model16, glm::vec3(1.0f, 2.0f, 16.0f));

        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model16));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));

        glUniform3fv(shaderList[0]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        glUniform3fv(shaderList[0]->GetUniformLocation("lightPos"), 1, (GLfloat *)&lightPos);
        glUniform3fv(shaderList[0]->GetUniformLocation("viewPos"), 1, (GLfloat *)&cameraPos);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, plant);
        meshList[1]->RenderMesh();

        // beach
        glm::mat4 model21(1.0f);
        model21 = glm::translate(model21, glm::vec3(1.0f, 2.0f, -12.0f));
        model21 = glm::scale(model21, glm::vec3(2.5f, 2.5f, 2.5f));

        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model21));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));

        glUniform3fv(shaderList[0]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        glUniform3fv(shaderList[0]->GetUniformLocation("lightPos"), 1, (GLfloat *)&lightPos);
        glUniform3fv(shaderList[0]->GetUniformLocation("viewPos"), 1, (GLfloat *)&cameraPos);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, white);
        meshList[9]->RenderMesh();

        // slide
        glm::mat4 model22(1.0f);
        model22 = glm::translate(model22, glm::vec3(-4.0f, 2.0f, 21.0f));
        model22 = glm::scale(model22, glm::vec3(0.04f, 0.04f, 0.04f));

        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model22));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));

        glUniform3fv(shaderList[0]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        glUniform3fv(shaderList[0]->GetUniformLocation("lightPos"), 1, (GLfloat *)&lightPos);
        glUniform3fv(shaderList[0]->GetUniformLocation("viewPos"), 1, (GLfloat *)&cameraPos);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, slider);
        meshList[10]->RenderMesh();

        // beach top
        glm::mat4 model23(1.0f);
        model23 = glm::translate(model23, glm::vec3(10.0f, 18.0f, 14.8f));
        model23 = glm::scale(model23, glm::vec3(2.5f, 2.5f, 2.5f));

        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model23));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));

        glUniform3fv(shaderList[0]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        glUniform3fv(shaderList[0]->GetUniformLocation("lightPos"), 1, (GLfloat *)&lightPos);
        glUniform3fv(shaderList[0]->GetUniformLocation("viewPos"), 1, (GLfloat *)&cameraPos);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, white);
        meshList[9]->RenderMesh();

        // light setup
        shaderList[1]->UseShader();
        uniformModel = shaderList[1]->GetUniformLocation("model");
        uniformView = shaderList[1]->GetUniformLocation("view");
        uniformProjection = shaderList[1]->GetUniformLocation("projection");

        glm::mat4 lightModel1(1.0f);
        lightModel1 = glm::translate(lightModel1, glm::vec3(22.8f, 11.0f, -7.5f));
        lightModel1 = glm::scale(lightModel1, glm::vec3(0.8f, 0.8f, 0.8f));
        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(lightModel1));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));
        glUniform3fv(shaderList[1]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        light->RenderMesh();

        glm::mat4 lightModel2(1.0f);
        lightModel2 = glm::translate(lightModel2, glm::vec3(22.8f, 11.0f, -20.0f));
        lightModel2 = glm::scale(lightModel2, glm::vec3(0.8f, 0.8f, 0.8f));
        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(lightModel2));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));
        glUniform3fv(shaderList[1]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        light->RenderMesh();

        glm::mat4 model17(1.0f);
        model17 = glm::translate(model17, glm::vec3(1.5f, 2.0f, 10.0f));
        model17 = glm::scale(model17, glm::vec3(0.3f, 0.3f, 0.3f));
        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model17));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));
        glUniform3fv(shaderList[1]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        windowL->RenderMesh();

        // window up1
        glm::mat4 model18(1.0f);
        model18 = glm::translate(model18, glm::vec3(1.2f, 12.0f, 14.5f));
        model18 = glm::scale(model18, glm::vec3(0.2f, 0.2f, 0.2f));
        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model18));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));
        glUniform3fv(shaderList[1]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        windowL->RenderMesh();

        // window up2
        glm::mat4 model19(1.0f);
        model19 = glm::translate(model19, glm::vec3(16.0f, 10.0f, 1.7f));
        model19 = glm::scale(model19, glm::vec3(0.3f, 0.3f, 0.3f));
        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model19));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));
        glUniform3fv(shaderList[1]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        windowM->RenderMesh();

        // window down2
        glm::mat4 model20(1.0f);
        model20 = glm::translate(model20, glm::vec3(14.0f, 4.0f, -6.0f));
        model20 = glm::scale(model20, glm::vec3(0.2f, 0.2f, 0.2f));
        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model20));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));
        glUniform3fv(shaderList[1]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        windowM->RenderMesh();

        // light pool
        glm::mat4 lightModel3(1.0f);
        lightModel3 = glm::translate(lightModel3, glm::vec3(-21.15f, 1.8f, -19.0f));
        lightModel3 = glm::scale(lightModel3, glm::vec3(0.3f, 0.3f, 0.3f));
        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(lightModel3));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));
        glUniform3fv(shaderList[1]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        light->RenderMesh();

        glm::mat4 lightModel4(1.0f);
        lightModel4 = glm::translate(lightModel4, glm::vec3(-21.15f, 1.8f, -9.0f));
        lightModel4 = glm::scale(lightModel4, glm::vec3(0.3f, 0.3f, 0.3f));
        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(lightModel4));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));
        glUniform3fv(shaderList[1]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        light->RenderMesh();

        glm::mat4 lightModel5(1.0f);
        lightModel5 = glm::translate(lightModel5, glm::vec3(-21.15f, 1.8f, 1.0f));
        lightModel5 = glm::scale(lightModel5, glm::vec3(0.3f, 0.3f, 0.3f));
        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(lightModel5));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));
        glUniform3fv(shaderList[1]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        light->RenderMesh();

        glm::mat4 lightModel6(1.0f);
        lightModel6 = glm::translate(lightModel6, glm::vec3(-21.15f, 1.8f, 11.0f));
        lightModel6 = glm::scale(lightModel6, glm::vec3(0.3f, 0.3f, 0.3f));
        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(lightModel6));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));
        glUniform3fv(shaderList[1]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        light->RenderMesh();

        glm::mat4 lightModel7(1.0f);
        lightModel7 = glm::translate(lightModel7, glm::vec3(-21.15f, 1.8f, 21.0f));
        lightModel7 = glm::scale(lightModel7, glm::vec3(0.3f, 0.3f, 0.3f));
        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(lightModel7));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));
        glUniform3fv(shaderList[1]->GetUniformLocation("lightColour"), 1, (GLfloat *)&lightColour);
        light->RenderMesh();

        glUseProgram(0);
        // end draw
        mainWindow.swapBuffers();
    }
    return 0;
}
