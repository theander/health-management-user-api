const Sequelize = require("sequelize");
require('dotenv').config()

const dbName = process.env.DATABASE_NAME;
const dbUser = process.env.DATABASE_USER;
const dbHost = process.env.DATABASE_HOST;
const dbPassword = process.env.DATABASE_PASSWORD;
const dbPort = process.env.DATABASE_PORT

const sequelize = new Sequelize(dbName, dbUser, dbPassword, {
    host: dbHost,
    dialect: 'mysql',
    port: dbPort
});
sequelize.sync()

module.exports = {sequelize};
