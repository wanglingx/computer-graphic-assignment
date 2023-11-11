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

float yaw =0.0f , pitch =0.0f;

//Vertex Shader
static const char* vShader = "Shaders/shader.vert";

//Fragment Shader
static const char* fShader = "Shaders/shader.frag";
void Createsquare()
{
    GLfloat vertices[] =
    {
        // -1.0f, -1.0f, 0.0f, //0
        // 0.0f,  -1.0f, 1.0f, //1
        // 1.0f, -1.0f, 0.0f, //2
        // 0.0f, 1.0f, 0.0f //3
        //on
         -1.0f, 20.0f, 1.0f,       //1.0f,1.0f,1.0f,     0.0f, 1.0f, 0.0f,
        -1.0f, 20.0f, -3.0f,     // 1.0f,1.0f,1.0f,     0.0f, 1.0f,-0.0f,
        5.0f, 20.0f, -3.0f,     //1.0f,1.0f,1.0f,    -0.0f, 1.0f,-0.0f,
        5.0f, 20.0f, 1.0f,      //1.0f,1.0f,1.0f,    -0.0f, 1.0f, 0.0f,
        
        // down
        5.0f, -1.0f, 1.0f,      //1.0f,1.0f,1.0f,     0.0f, -1.0f, 0.0f,
        5.0f, -1.0f, -3.0f,  //1.0f,1.0f,1.0f,     0.0f, -1.0f,-0.0f,     
        -1.0f, -1.0f, -3.0f,    //1.0f,1.0f,1.0f,    -0.0f, -1.0f,-0.0f,   
        -1.0f, -1.0f, 1.0f,     //1.0f,1.0f,1.0f,    -0.0f, -1.0f, 0.0f,   

        // left
        -1.0f, 20.0f, 1.0f,      //1.0f,1.0f,1.0f,    -1.0f, 0.0f, 0.0f,
        -1.0f, 20.0f, -3.0f,     //1.0f,1.0f,1.0f,    -1.0f, 0.0f,-0.0f,
        -1.0f, -1.0f, -3.0f,    //1.0f,1.0f,1.0f,    -1.0f,-0.0f,-0.0f,
        -1.0f, -1.0f, 1.0f,     //1.0f,1.0f,1.0f,    -1.0f,-0.0f, 0.0f,

        // right
        5.0f, 20.0f, 1.0f,       //1.0f,1.0f,1.0f,      1.0f, 0.0f, 0.0f,
        5.0f, 20.0f, -3.0f,      //1.0f,1.0f,1.0f,      1.0f, 0.0f, 0.0f,   
        5.0f, -1.0f, -3.0f,     //1.0f,1.0f,1.0f,      1.0f, 0.0f, 0.0f,   
        5.0f, -1.0f, 1.0f,      //1.0f,1.0f,1.0f,      1.0f, 0.0f, 0.0f,   

        // front
        5.0f, 20.0f, 1.0f,       //1.0f,1.0f,1.0f,      0.0f, 0.0f, 1.0f,
        5.0f, -1.0f, 1.0f,      //1.0f,1.0f,1.0f,      0.0f,-0.0f, 1.0f,   
        -1.0f, -1.0f, 1.0f,     //1.0f,1.0f,1.0f,     -0.0f,-0.0f, 1.0f,   
        -1.0f, 20.0f, 1.0f,      //1.0f,1.0f,1.0f,     -0.0f, 0.0f, 1.0f,   

        // back
        5.0f, 20.0f, -3.0f,      //1.0f,1.0f,1.0f,      0.0f, 0.0f, -1.0f,
        5.0f, -1.0f, -3.0f,     //1.0f,1.0f,1.0f,      0.0f,-0.0f, -1.0f,       
        -1.0f, -1.0f, -3.0f,    //1.0f,1.0f,1.0f,     -0.0f,-0.0f, -1.0f,       
        -1.0f, 20.0f, -3.0f,     //1.0f,1.0f,1.0f,     -0.0f, 0.0f, -1.0f,

    };

    unsigned int indices[] = 
    {
        // 0, 3, 1,
        // 1, 3, 2,
        // 2, 3, 0,
        // 0, 1, 2,
        // 0, 1, 2,
        // 2, 3, 0,
        0,1,2,
        2,3,0,

         4, 5, 6,
        6, 7, 4,
        
        8, 9, 10,
        10, 11, 8,
        
        12, 13, 14,
        14, 15, 12,
        
         16, 17, 18,
         18, 19, 16,
        
        20, 21, 22,
        22, 23, 20, 
    };
    

    Mesh *obj1 = new Mesh();
    obj1->CreateMesh(vertices, indices, 24*3, 12*3);
    meshList.push_back(obj1);

    // Mesh *obj2 = new Mesh();
    // obj2->CreateMesh(vertices, indices, 24*3, 12*3);
    // meshList.push_back(obj2);
}

void CreateTriangle()
{
    GLfloat vertices[] =
    {
        -1.0f, -1.0f, 0.0f,     //0
        0.0f,  -1.0f, 1.0f,    //1
        1.0f, -1.0f, 0.0f,     //2
        0.0f, 1.0f, 0.0f       //3
        //  1.0f, 1.0f, 1.0f,       //1.0f,1.0f,1.0f,     0.0f, 1.0f, 0.0f,
        // 1.0f, 1.0f, -1.0f,     // 1.0f,1.0f,1.0f,     0.0f, 1.0f,-0.0f,
        // -1.0f, 1.0f, -1.0f,     //1.0f,1.0f,1.0f,    -0.0f, 1.0f,-0.0f,
        // -1.0f, 1.0f, 1.0f,      //1.0f,1.0f,1.0f,    -0.0f, 1.0f, 0.0f,
        
        // // down
        // 1.0f, -1.0f, 1.0f,      //1.0f,1.0f,1.0f,     0.0f, -1.0f, 0.0f,
        // 1.0f, -1.0f, -1.0f,     //1.0f,1.0f,1.0f,     0.0f, -1.0f,-0.0f,     
        // -1.0f, -1.0f, -1.0f,    //1.0f,1.0f,1.0f,    -0.0f, -1.0f,-0.0f,   
        // -1.0f, -1.0f, 1.0f,     //1.0f,1.0f,1.0f,    -0.0f, -1.0f, 0.0f,   

        // // left
        // -1.0f, 1.0f, 1.0f,      //1.0f,1.0f,1.0f,    -1.0f, 0.0f, 0.0f,
        // -1.0f, 1.0f, -1.0f,     //1.0f,1.0f,1.0f,    -1.0f, 0.0f,-0.0f,
        // -1.0f, -1.0f, -1.0f,    //1.0f,1.0f,1.0f,    -1.0f,-0.0f,-0.0f,
        // -1.0f, -1.0f, 1.0f,     //1.0f,1.0f,1.0f,    -1.0f,-0.0f, 0.0f,

        // // right
        // 1.0f, 1.0f, 1.0f,       //1.0f,1.0f,1.0f,      1.0f, 0.0f, 0.0f,
        // 1.0f, 1.0f, -1.0f,      //1.0f,1.0f,1.0f,      1.0f, 0.0f, 0.0f,   
        // 1.0f, -1.0f, -1.0f,     //1.0f,1.0f,1.0f,      1.0f, 0.0f, 0.0f,   
        // 1.0f, -1.0f, 1.0f,      //1.0f,1.0f,1.0f,      1.0f, 0.0f, 0.0f,   

        // // front
        // 1.0f, 1.0f, 1.0f,       //1.0f,1.0f,1.0f,      0.0f, 0.0f, 1.0f,
        // 1.0f, -1.0f, 1.0f,      //1.0f,1.0f,1.0f,      0.0f,-0.0f, 1.0f,   
        // -1.0f, -1.0f, 1.0f,     //1.0f,1.0f,1.0f,     -0.0f,-0.0f, 1.0f,   
        // -1.0f, 1.0f, 1.0f,      //1.0f,1.0f,1.0f,     -0.0f, 0.0f, 1.0f,   

        // // back
        // 1.0f, 1.0f, -1.0f,      //1.0f,1.0f,1.0f,      0.0f, 0.0f, -1.0f,
        // 1.0f, -1.0f, -1.0f,     //1.0f,1.0f,1.0f,      0.0f,-0.0f, -1.0f,       
        // -1.0f, -1.0f, -1.0f,    //1.0f,1.0f,1.0f,     -0.0f,-0.0f, -1.0f,       
        // -1.0f, 1.0f, -1.0f,     //1.0f,1.0f,1.0f,     -0.0f, 0.0f, -1.0f,

    };

    unsigned int indices[] = 
    {
        0, 3, 1,
        1, 3, 2,
        2, 3, 0,
        0, 1, 2,
        // 0, 1, 2,
        // 2, 3, 0,
        
        // 4, 5, 6,
        // 6, 7, 4,
        
        // 8, 9, 10,
        // 10, 11, 8,
        
        // 12, 13, 14,
        // 14, 15, 12,
        
        // 16, 17, 18,
        // 18, 19, 16,
        
        // 20, 21, 22,
        // 22, 23, 20, 
    };

    // Mesh *obj1 = new Mesh();
    // obj1->CreateMesh(vertices, indices, 12,12);
    // meshList.push_back(obj1);

    Mesh *obj2 = new Mesh();
    obj2->CreateMesh(vertices, indices, 24*3, 12*3);
    meshList.push_back(obj2);
}

void CreateShaders()
{
    Shader* shader1 = new Shader();
    shader1->CreateFromFiles(vShader, fShader);
    shaderList.push_back(*shader1);
}

void mouse_callback(GLFWwindow* window , double xPos , double yPos)
{
    static float lastX = mainWindow.getBufferWidth()/2;
    static float lastY = mainWindow.getBufferHeight()/2;

    float xoffset = xPos - lastX;
    float yoffset = lastY - yPos;

    lastX = xPos;
    lastY = yPos;

    float sensitivity =0.2f;

    xoffset *=sensitivity;
    yoffset *=sensitivity;

    yaw += xoffset;
    pitch += yoffset;

    
    
    if(pitch > 89.0f)
        pitch = 89.0f;
    if(pitch < -89.0f)
        pitch = -89.0;

    
}

int main()
{
    mainWindow = Window(WIDTH, HEIGHT, 3, 1);
    mainWindow.initialise();

    // CreateTriangle();
    Createsquare();
    CreateShaders();

    GLuint uniformModel = 0,  uniformProjection = 0 , uniformView = 0;

    // glm::mat4 projection = glm::ortho(-4.0f,4.0f,-3.0f,3.0f,1.0f,100.0f);

     glm::mat4 projection = glm::perspective(45.0f, (GLfloat)mainWindow.getBufferWidth()/ (GLfloat)mainWindow.getBufferHeight(), 0.1f, 100.0f);

    glm::vec3 cameraPos = glm::vec3(0.0f, 0.0f, -5.0f);
    glm::vec3 cameraTarget = glm::vec3(0.0f, 1.0f, -1.0f);
    glm::vec3 up = glm::vec3(0.0f, 0.1f, 0.0f);

    glm::vec3 cameraDirection = glm::normalize(cameraTarget - cameraPos);
    glm::vec3 cameraRight = glm::normalize(glm::cross(cameraDirection, up));
    glm::vec3 cameraUp = glm::normalize(glm::cross(cameraDirection, -cameraRight));

    glfwSetCursorPosCallback(mainWindow.getWindow(), mouse_callback);
    //Loop until window closed
    while (!mainWindow.getShouldClose())
    {
        //Get + Handle user input events
        glfwPollEvents();
        glm::vec3 direction;
        direction.x = cos(glm::radians(pitch)) * cos(glm::radians(yaw));
        direction.y = sin(glm::radians(pitch));
        direction.z = cos(glm::radians(pitch)) * sin(glm::radians(yaw));
        cameraDirection = glm::normalize(direction);
        
        cameraRight = glm::normalize(glm::cross(cameraDirection, up));
        cameraUp = glm::normalize(glm::cross(cameraDirection, -cameraRight));

        
        if(glfwGetKey(mainWindow.getWindow(), GLFW_KEY_W) == GLFW_PRESS){
            cameraPos += cameraDirection * 0.001f;
        }
        if(glfwGetKey(mainWindow.getWindow(), GLFW_KEY_S) == GLFW_PRESS){
            cameraPos -= cameraDirection * 0.001f;
        }
        if(glfwGetKey(mainWindow.getWindow(), GLFW_KEY_A) == GLFW_PRESS){
            cameraPos -= cameraRight * 0.001f;
        }
        if(glfwGetKey(mainWindow.getWindow(), GLFW_KEY_D) == GLFW_PRESS){
            cameraPos += cameraRight * 0.001f;
        }
        if(glfwGetKey(mainWindow.getWindow(), GLFW_KEY_SPACE) == GLFW_PRESS){
            cameraPos += cameraUp * 0.001f;
        }
        else if(glfwGetKey(mainWindow.getWindow(), GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS){
            cameraPos -= cameraUp * 0.001f;
        }

        //Clear window
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        //draw here
        shaderList[0].UseShader();
        uniformModel = shaderList[0].GetUniformLocation("model");
        uniformView = shaderList[0].GetUniformLocation("view");
        uniformProjection = shaderList[0].GetUniformLocation("projection");

        //Object
       
        glm::mat4 model (1.0f);

        //camera
        glm::mat4 view (1.0f);
       
        glm::vec3 up = glm::vec3(0.0f,1.0f,0.0f);

        // glm::mat4 cameraRotateMat (1.0f);
        // cameraRotateMat[0]=glm::vec4 (cameraRight.x,cameraUp.x,cameraDirection.x,0.0f);
        // cameraRotateMat[1]=glm::vec4 (cameraRight.y,cameraUp.y,cameraDirection.y,0.0f);
        // cameraRotateMat[2]=glm::vec4 (-cameraRight.z,cameraUp.z,-cameraDirection.z,0.0f);

        // glm::mat4 cameraPosMat(1.0f);
        // cameraPosMat[0][3] = cameraPos.x;
        // cameraPosMat[1][3] = cameraPos.y;
        // cameraPosMat[2][3] = cameraPos.z;

        // glm::vec3 direction;
        // direction.x = cos(glm::radians(yaw)) * cos(glm::radians(pitch));

        // direction.y = sin(glm::radians(pitch));

        // direction.z = sin(glm::radians(pitch)) * cos(glm::radians(pitch));

        // cameraDirection = glm::normalize(direction);
        // cameraRight = glm::normalize(glm::cross(cameraDirection, up));
        // cameraUp = glm::normalize(glm::cross(cameraDirection, -cameraRight));
        

        // //glm::vec3 cameraDirectionNoY = glm::vec3(cameraDirection.x,0.0f,cameraDirection.z);

        //  if(glfwGetKey(mainWindow.getWindow(),GLFW_KEY_W) == GLFW_PRESS)
        //     cameraPos += cameraDirection * 0.001f;
        // if(glfwGetKey(mainWindow.getWindow(),GLFW_KEY_S) == GLFW_PRESS)
        //     cameraPos -= cameraDirection * 0.001f;
        // if(glfwGetKey(mainWindow.getWindow(),GLFW_KEY_A) == GLFW_PRESS)
        //     cameraPos -= cameraRight * 0.001f;
        // if(glfwGetKey(mainWindow.getWindow(),GLFW_KEY_D) == GLFW_PRESS)
        //     cameraPos += cameraRight * 0.001f;
        // // if(glfwGetKey(mainWindow.getWindow(),GLFW_KEY_SPACE) == GLFW_PRESS)
        // //     cameraPos += cameraPos * 0.1f;


        view =glm::lookAt (cameraPos ,cameraPos + cameraDirection,up);

       

        model = glm::translate(model, glm::vec3(0.0f, 0.0f, -2.5f));
        //model = glm::rotate(model, 90.0f * 3.1416f / 180.0f, glm::vec3(0.0f, 0.0f, -2.5f));
        model = glm::scale(model, glm::vec3(0.4f, 0.4f, 1.0f));


        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));

        meshList[0]->RenderMesh();

       

        // //obj2
        // glm::mat4 model2 (1.0f);

        // model2 = glm::translate(model2, glm::vec3(-1.5f, 0.0f, -3.5f));
        // model2 = glm::rotate(model2, 20.0f * 3.1416f / 180.0f, glm::vec3(0.0f, 0.0f, -2.0f));
        // model2 = glm::scale(model2, glm::vec3(0.4f, 0.4f, 0.4f));


        // glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model2));
        // glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));

        // meshList[1]->RenderMesh();

        glUseProgram(0);
        //end draw

        mainWindow.swapBuffers();
    }

    return 0;
}