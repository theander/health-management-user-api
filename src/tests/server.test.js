const app = require("../../server");
const supertest = require("supertest");

test("GET /login", async () => {

    await supertest(app).get("/login")
        .expect(200)
        .then((response) => {
            // Check type and length
            expect(response.body).toBeTruthy();
            expect(response.body).toEqual({"Password": "pass", "accessType": "S_Admin", "name": "User"});

            // Check data
            expect(response.body.Password).toBe("pass");
            expect(response.body.accessType).toBe("S_Admin");
            expect(response.body.name).toBe("User");
        });
});

test("GET /login", async () => {

    await supertest(app).get("/login1")
        .expect(404)

});