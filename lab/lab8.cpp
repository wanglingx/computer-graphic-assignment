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

const GLint WIDTH = 800, HEIGHT = 600;
//int angle = 45;

Window mainWindow;
std::vector<Mesh *> meshList;
std::vector<Shader> shaderList;

float yaw = 0.0f;
float pitch = 0.0f;

// Vertex Shader
static const char *vShader = "Shaders/shader.vert";

// Fragment Shader
static const char *fShader = "Shaders/shader.frag";

// create plot
void CreateTriangle()
{
    //local space
    GLfloat vertices[] =
        {
            // x y z is a position of point from clip space
            -1.0f, -1.0f, 0.0f, // 0
            0.0f, -1.0f, 1.0f,  // 1
            1.0f, -1.0f, 0.0f,  // 2
            0.0f, 1.0f, 0.0f    // 3
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
    for (int i = 0; i < 10; i++)
    {
        obj1->CreateMesh(vertices, indices, 12, 12);
        meshList.push_back(obj1);
    }
    // or
    //  Mesh *obj2 = new Mesh();
    //  obj2->CreateMesh(vertices, indices, 12, 12);
    //  meshList.push_back(obj2);
}

void CreateShaders()
{
    Shader *shader1 = new Shader();
    shader1->CreateFromFiles(vShader, fShader);
    shaderList.push_back(*shader1);
}

void mouse_callback(GLFWwindow* window,double xPos,double yPos)
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

    yaw   += xOffset;
    pitch += yOffset;

    if(pitch > 89.0f)
        pitch = 89.0f;
    if(pitch < -89.0f)
        pitch = -89.0f;

}

int main()
{
    // create window to show graphic
    // create obj (major ver,miner ver)
    mainWindow = Window(WIDTH, HEIGHT, 3, 1);
    mainWindow.initialise();

    CreateTriangle();
    CreateShaders();

    // tell where position
    GLuint uniformModel = 0, uniformProjection = 0, uniformView = 0;

    //projection matrix
    //glm::mat4 projection = glm::ortho(-4.0f, 4.0f, -3.0f, 3.0f, 0.1f, 100.0f);//(l,r,b,l,n,f)
    glm::mat4 projection = glm::perspective(45.0f, (GLfloat)mainWindow.getBufferWidth() / (GLfloat)mainWindow.getBufferHeight(), 0.1f, 100.0f);

    // glm::vec3 cameraPos = glm::vec3(0.0f, 0.0f, 0.0f); // intto -x axis
    // glm::vec3 cameraTarget = glm::vec3(0.0f, 0.0f, -1.0f);
    // glm::vec3 cameraDirection = glm::normalize(cameraTarget - cameraPos);
    

    // Loop until window closed
    while (!mainWindow.getShouldClose())
    {
        // Get + Handle user input events
        glfwPollEvents();

        // Clear window
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        // draw here
        shaderList[0].UseShader();
        // call position in shader file
        uniformModel = shaderList[0].GetModelLocation();
        uniformModel = shaderList[0].GetUniformLocation("view");
        uniformProjection = shaderList[0].GetProjectionLocation();

        // Object

        //glfwSetCursorPosCallback(mainWindow.getWindow(), mouse_callback);

        glm::vec3 pyramidPosition[] = {
            glm::vec3(0.0f, 0.0f, -2.5f),
            glm::vec3(2.0f, 5.0f, -15.0f),
            glm::vec3(-1.5f, -2.2f, -2.5f),
            glm::vec3(-3.8f, -2.0f, -12.3f),
            glm::vec3(2.4f, -0.4f, -3.5f),
            glm::vec3(-1.7f, 3.0f, -7.5f),
            glm::vec3(1.3f, -2.0f, -2.5f),
            glm::vec3(1.5f, 2.0f, -2.5f),
            glm::vec3(1.5f, 0.2f, -1.5f),
            glm::vec3(-1.3f, 1.0f, -1.5f),
        };

        glm::mat4 view(1.0f);
        // Task1 Move the camera to the position (1.0f, 0.5f, 2.0f) and look at the position (0.0f, -0.3f,-1.0f)
        glm::vec3 cameraPos = glm::vec3(0.0f, 0.0f, 10.0f); //intto -x axis
        glm::vec3 cameraTarget = glm::vec3(1.0f, 0.5f, 2.0f);
        glm::vec3 cameraDirection = glm::normalize(cameraTarget - cameraPos);

        glm::vec3 up = glm::vec3(0.0f, -0.3f, -1.0f);
        glm::vec3 cameraRight = glm::normalize(glm::cross(cameraDirection, up));
        glm::vec3 cameraUp = glm::normalize(glm::cross(cameraDirection, -cameraRight));

        glm::mat4 cameraPosMat(1.0f);
        cameraPosMat[0][3] = -cameraPos.x;
        cameraPosMat[1][3] = -cameraPos.y;
        cameraPosMat[2][3] = -cameraPos.z;

        glm::mat4 cameraRotateMat(1.0f);
        cameraRotateMat[0] = glm::vec4(cameraRight.x, cameraUp.x, -cameraDirection.x, 0.0f);
        cameraRotateMat[1] = glm::vec4(cameraRight.y, cameraUp.y, -cameraDirection.y, 0.0f);
        cameraRotateMat[2] = glm::vec4(cameraRight.z, cameraUp.z, -cameraDirection.z, 0.0f);
        
        view = glm::lookAt(cameraPos, cameraPos + cameraDirection, up);

        // glm::vec3 direction(1.0f);
        // direction.x = cos(glm::radians(yaw)) * cos(glm::radians(pitch));
        // direction.y = sin(glm::radians(pitch));
        // direction.z = sin(glm::radians(yaw)) * cos(glm::radians(pitch));
        // cameraDirection = glm::normalize(direction);

        // glm::vec3 cameraRight = glm::normalize(glm::cross(cameraDirection, up));
        // glm::vec3 cameraUp = glm::normalize(glm::cross(cameraDirection, -cameraRight));

        // glm::vec3 cameraDirectionY = glm::vec3(cameraDirection.x, 0.0f, cameraDirection.z);

        // //view = glm::lookAt(cameraPos, cameraTarget, up);
        // //move camera
        // if(glfwGetKey(mainWindow.getWindow(),GLFW_KEY_W) == GLFW_PRESS)
        //     cameraPos += cameraDirectionY * 0.01f;

        // if (glfwGetKey(mainWindow.getWindow(), GLFW_KEY_S) == GLFW_PRESS)
        //     cameraPos -= cameraDirectionY * 0.01f;

        // if (glfwGetKey(mainWindow.getWindow(), GLFW_KEY_A) == GLFW_PRESS)
        //     cameraPos -= cameraRight * 0.01f;

        // if (glfwGetKey(mainWindow.getWindow(), GLFW_KEY_D) == GLFW_PRESS)
        //     cameraPos += cameraRight * 0.01f;

        // view = glm::lookAt(cameraPos, cameraPos + cameraDirection, up);

        for (int i = 0; i < 10; i++)
        {
            
            glm::mat4 model(1.0f);
            model = glm::translate(model, pyramidPosition[i]);
            model = glm::rotate(model, glm::radians(2.0f*i), glm::vec3(0.1f, 0.3f, 0.5f));
            model = glm::scale(model, glm::vec3(0.8f, 0.8f, 1.0f));
            // model = glm::translate(model,glm::vec3(0.0f,1.0f,0.0f));

            glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model));
            glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
            glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));
            meshList[i]->RenderMesh();
        }
        
        glUseProgram(0);
        // end draw
        mainWindow.swapBuffers();
    }

    return 0;
}
