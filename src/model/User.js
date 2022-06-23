const {sequelize} = require("../conf/userDb.js");
const Sequelize = require("sequelize");

const User = sequelize.define('user', {
    id: {
        type: Sequelize.INTEGER,
        autoIncrement: true,
        primaryKey: true
    },
    name: {
        type: Sequelize.STRING,
        allowNull: false
    },
    password: {
        type: Sequelize.STRING,
        allowNull: false
    },
    accessType: {
        type: Sequelize.STRING,
        allowNull: false
    }
})

module.exports = User;