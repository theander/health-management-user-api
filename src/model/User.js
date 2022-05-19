const Sequelize = require('sequelize');
const database = require('../conf/userDb');


const User = database.define('user', {
    id: {
        type: Sequelize.INTEGER,
        autoIncrement: true,
        allowNull: false,
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
export default User;