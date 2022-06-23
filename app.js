const dotenv = require('dotenv')
const express= require('express');
const app =express();
const routes = require("./src/routes/routes")
const swaggerUi = require('swagger-ui-express')
const swaggerFile = require('./swagger_output.json')
dotenv.config()
const PORT = process.env.PORT
app.use(routes)

app.use('/doc', swaggerUi.serve, swaggerUi.setup(swaggerFile))

//db.sync(() => console.log(`Banco de dados conectado: ${process.env.DB_NAME}`));
app.get("/status",
    (req, res) => {
        res.send("Page Running")
    })
app.listen(PORT,()=>{
    console.log("Server is running on port: "+PORT)
})