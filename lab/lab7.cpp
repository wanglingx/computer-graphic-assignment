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

Window mainWindow;
std::vector<Mesh*> meshList;
std::vector<Shader> shaderList;

//Vertex Shader
static const char* vShader = "Shaders/shader.vert";

//Fragment Shader
static const char* fShader = "Shaders/shader.frag";

//create plot
void CreateTriangle()
{
    GLfloat vertices[] = 
    {
        // x y z is a position of point from clip space
       -1.0f, -1.0f, 0.0f, //0
        0.0f, -1.0f, 1.0f, //1
        1.0f, -1.0f, 0.0f, //2
        0.0f,  1.0f, 0.0f  //3
    };

    //12 pic = 36 point
    unsigned int indices[] = 
    {
        0, 3, 1,
        1, 3, 2,
        2, 3, 0,
        0, 1, 2,
    };

    Mesh *obj1 = new Mesh();
    obj1->CreateMesh(vertices, indices, 12, 12);
    meshList.push_back(obj1);

    Mesh *obj2 = new Mesh();
    obj2->CreateMesh(vertices, indices, 12, 12);
    meshList.push_back(obj2);
}

void CreateShaders()
{
    Shader* shader1 = new Shader();
    shader1->CreateFromFiles(vShader, fShader);
    shaderList.push_back(*shader1);
}

int main()
{
    //create window to show graphic
    //create obj (major ver,miner ver)
    mainWindow = Window(WIDTH, HEIGHT, 3, 1); 
    mainWindow.initialise();

    CreateTriangle();
    CreateShaders();

    //tell where position
    GLuint uniformModel = 0, uniformProjection = 0;

    glm::mat4 projection = glm::perspective(45.0f,(GLfloat)mainWindow.getBufferWidth() / (GLfloat)mainWindow.getBufferHeight(),0.1f,100.0f);

    //Loop until window closed
    while (!mainWindow.getShouldClose())
    {
        //Get + Handle user input events
        glfwPollEvents();

        //Clear window
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        //draw here
        shaderList[0].UseShader();
        //call position in shader file
        uniformModel = shaderList[0].GetModelLocation();
        uniformProjection = shaderList[0].GetProjectionLocation();

        //Object
        glm::mat4 model(1.0f);
        //model = glm::rotate(model,90.0f * 3.1416f / 180.0f,glm::vec3(0.0f,0.0f,1.0f));

        //Task1 change scale 2 instead of 0.4 and translate Y axis
        model = glm::translate(model,glm::vec3(0.3f,0.0f,-2.5f));
        model = glm::scale(model,glm::vec3(2.0f,2.0f,1.0f));
        model = glm::translate(model,glm::vec3(0.0f,1.0f,0.0f));

        //Task2 model 1
        model = glm::translate(model,glm::vec3(0.3f,0.0f,-2.5f));
        model = glm::scale(model,glm::vec3(0.4f,0.4f,1.0f));

        glUniformMatrix4fv(uniformModel,1, GL_FALSE , glm::value_ptr (model));
        glUniformMatrix4fv(uniformProjection,1, GL_FALSE , glm::value_ptr (projection));
        meshList[0]->RenderMesh();

        model = glm::translate(model,glm::vec3(-4.0f,0.0f,-2.5f));
        model = glm::scale(model,glm::vec3(2.0f,2.0f,1.0f));
        
        glUniformMatrix4fv(uniformModel,1, GL_FALSE , glm::value_ptr (model));
        glUniformMatrix4fv(uniformProjection,1, GL_FALSE , glm::value_ptr (projection));
        meshList[1]->RenderMesh();

        glUseProgram(0);
        //end draw
        mainWindow.swapBuffers();
    }

    return 0;
}
