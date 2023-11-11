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

#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>

#include "Libs/stb_image.h"

const GLint WIDTH = 800, HEIGHT = 600;

Window mainWindow;
std::vector<Mesh *> meshList;
std::vector<Shader> shaderList;

float yaw = 0.0f;
float pitch = 0.0f;

// Vertex Shader
static const char *vShader = "Shaders/shader.vert";
// Fragment Shader
static const char *fShader = "Shaders/shader.frag";

void CreateTriangle()
{   // local space
    GLfloat vertices[] =
        {
            -1.0f, -1.0f, 0.0f, 0.0f, 0.0f,
             0.0f, -1.0f, 1.0f, 0.5f, 0.0f,
             1.0f, -1.0f, 0.0f, 1.0f, 0.0f,
             0.0f,  1.0f, 0.0f, 0.0f, 1.0f
        };

    // 12 pic = 36 point
    unsigned int indices[] =
        {
            0,3,1,
            1,3,2,
            2,3,0,
            0,1,2,
        };

    Mesh *obj1 = new Mesh();
    for (int j = 0; j < 2; j++)
    {
        obj1->CreateMesh(vertices, indices, 20, 12);
        meshList.push_back(obj1);
    }
}

void createRect()
{
    GLfloat vertices[] =
        {
            -1.0f, -1.0f, 0.0f, 0.0f, 0.0f,
             0.0f, -1.0f, 1.0f, 0.5f, 0.0f,
             1.0f, -1.0f, 0.0f, 1.0f, 0.0f,
             0.0f,  1.0f, 0.0f, 0.0f, 1.0f};

    unsigned int indices[] =
        {
            2,3,0,
        };

    Mesh *obj2 = new Mesh();
    for (int i = 2; i < 5; i++)
    {
        obj2->CreateMesh(vertices, indices, 20, 6);
        meshList.push_back(obj2);
    }
}

void CreateShaders()
{
    Shader *shader1 = new Shader();
    shader1->CreateFromFiles(vShader, fShader);
    shaderList.push_back(*shader1);
}

void mouse_callback(GLFWwindow *window, double xPos, double yPos)
{
    static float lastX = mainWindow.getBufferWidth() / 2.0f;
    static float lastY = mainWindow.getBufferHeight() / 2.0f;

    float xOffset = xPos - lastX;
    float yOffset = lastY - yPos;

    lastX = xPos;
    lastY = yPos;

    float sensitivity = 0.2f;

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
    // create window to show graphic
    mainWindow = Window(WIDTH, HEIGHT, 3, 1);
    mainWindow.initialise();

    CreateTriangle();
    createRect();
    CreateShaders();

    GLuint uniformModel = 0, uniformProjection = 0, uniformView = 0;
    glm::mat4 projection = glm::perspective(45.0f, (GLfloat)mainWindow.getBufferWidth() / (GLfloat)mainWindow.getBufferHeight(), 0.1f, 100.0f);
    // texture sand1
    unsigned int texture;
    glGenTextures(1, &texture);
    glBindTexture(GL_TEXTURE_2D, texture);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

    int width, height, nrChannels;
    unsigned char *data = stbi_load("Textures/sand6.jpg", &width, &height, &nrChannels, 0);
    if (data)
    {
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, data);
        glGenerateMipmap(GL_TEXTURE_2D);
    }
    else
    {
        std::cout << "Failed to load texture" << std::endl;
    }
    stbi_image_free(data);

    // texture sand2
    unsigned int texture0;
    glGenTextures(1, &texture0);
    glBindTexture(GL_TEXTURE_2D, texture0);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

    int width0, height0, nrChannels0;
    unsigned char *data0 = stbi_load("Textures/sand7.jpg", &width0, &height0, &nrChannels0, 0);
    if (data0)
    {
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width0, height0, 0, GL_RGB, GL_UNSIGNED_BYTE, data0);
        glGenerateMipmap(GL_TEXTURE_2D);
    }
    else
    {
        std::cout << "Failed to load texture" << std::endl;
    }
    stbi_image_free(data0);

    // texture pyramid 1
    unsigned int texture2;
    glGenTextures(1, &texture2);
    glBindTexture(GL_TEXTURE_2D, texture2);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

    int width2, height2, nrChannels2;
    unsigned char *data2 = stbi_load("Textures/py.jpg", &width2, &height2, &nrChannels2, 0);
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

    // texture pyramid 2
    unsigned int texture3;
    glGenTextures(1, &texture3);
    glBindTexture(GL_TEXTURE_2D, texture3);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

    int width3, height3, nrChannels3;
    unsigned char *data3 = stbi_load("Textures/py1.jpg", &width3, &height3, &nrChannels3, 0);
    if (data3)
    {
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width3, height3, 0, GL_RGB, GL_UNSIGNED_BYTE, data3);
        glGenerateMipmap(GL_TEXTURE_2D);
    }
    else
    {
        std::cout << "Failed to load texture" << std::endl;
    }
    stbi_image_free(data3);

    while (!mainWindow.getShouldClose())
    {
        // Get + Handle user input events
        glfwPollEvents();
        // Clear window
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        // camera position
        glm::vec3 cameraPos = glm::vec3(0.0f, 0.0f, 3.0f); // intto -x axis
        glm::vec3 cameraTarget = glm::vec3(0.0f, 0.3f, -1.0f);
        glm::vec3 cameraDirection = glm::normalize(cameraTarget - cameraPos);
        glm::vec3 up = glm::vec3(0.0f, 1.0f, 0.0f);
        glm::vec3 cameraRight = glm::normalize(glm::cross(cameraDirection, up));
        glm::vec3 cameraUp = glm::normalize(glm::cross(cameraDirection, -cameraRight));

        // draw here
        shaderList[0].UseShader();
        uniformModel = shaderList[0].GetUniformLocation("model");
        uniformModel = shaderList[0].GetUniformLocation("view");
        uniformProjection = shaderList[0].GetUniformLocation("projection");
        // glfwSetCursorPosCallback(mainWindow.getWindow(), mouse_callback);

        //object position
        glm::vec3 pyramidPosition[] = {
            glm::vec3(0.0f, 0.0f, 0.0f),
            glm::vec3(1.44f, 0.0f, -2.0f),
        };

        glm::vec3 wall[] = {
            glm::vec3(-2.2f, -1.0f, -2.0f),
            glm::vec3(-2.2f, -1.5f, -2.0f),
        };

        // view
        glm::mat4 view(1.0f);
        glm::mat4 cameraPosMat(1.0f);
        cameraPosMat[0][3] = cameraPos.x;
        cameraPosMat[1][3] = cameraPos.y;
        cameraPosMat[2][3] = cameraPos.z;

        glm::mat4 cameraRotateMat(1.0f);
        cameraRotateMat[0] = glm::vec4(cameraRight.x, cameraUp.x, cameraDirection.x, 0.0f);
        cameraRotateMat[1] = glm::vec4(cameraRight.y, cameraUp.y, cameraDirection.y, 0.0f);
        cameraRotateMat[2] = glm::vec4(-cameraRight.z, -cameraUp.z, -cameraDirection.z, 0.0f);

        view = glm::lookAt(cameraPos, cameraPos + cameraDirection, up);

        //pyramid 1
        glm::mat4 model(1.0f);
        model = glm::translate(model, pyramidPosition[0]);
        model = glm::rotate(model, glm::radians(2.0f * 0), glm::vec3(0.1f, 0.3f, 0.5f));
        model = glm::scale(model, glm::vec3(1.5f, 0.8f, 1.0f));
        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture2);
        meshList[0]->RenderMesh(); 

        // pyramid 2
        glm::mat4 model0(1.0f);
        model0 = glm::translate(model0, pyramidPosition[1]);
        model0 = glm::rotate(model0, glm::radians(2.0f * 1), glm::vec3(0.1f, 0.3f, 0.5f));
        model0 = glm::scale(model0, glm::vec3(1.5f, 0.8f, 1.0f));
        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model0));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture3);
        meshList[1]->RenderMesh(); 

        //sand
        glm::mat4 model1(1.0f);
        model1 = glm::translate(model1, glm::vec3(-2.6f, -1.5f, -2.0f));
        model1 = glm::scale(model1, glm::vec3(2.7f, 1.1f, 1.0f));
        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model1));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture);
        meshList[2]->RenderMesh();

        glm::mat4 model2(1.0f);
        model2 = glm::translate(model2, glm::vec3(2.6f, -1.5f, -2.0f));
        model2 = glm::scale(model2, glm::vec3(2.7f, 1.1f, 1.0f));
        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model2));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture0);
        meshList[3]->RenderMesh(); 

        glm::mat4 model3(1.0f);
        model3 = glm::translate(model3, glm::vec3(0.0f, 0.7f, -2.0f));
        model3 = glm::rotate(model3, glm::radians(180.0f), glm::vec3(0.0f, -0.918f, -2.0f));
        model3 = glm::scale(model3, glm::vec3(2.55f, 1.1f, 1.0f));
        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model3));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture0);
        meshList[4]->RenderMesh();

        glUseProgram(0);
        mainWindow.swapBuffers();
    }
    return 0;
}
