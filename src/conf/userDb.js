import 'dotenv/config';

const database = process.env.DATABASE_NAME
const userName = process.env.DATABASE_USER
const password = process.env.DATABASE_PASSWORD

const {Sequelize} = require('sequelize');

const sequelize = new Sequelize(database, userName, password , {
    host: 'localhost',
    dialect: 'mysql'
});

export default sequelize;

