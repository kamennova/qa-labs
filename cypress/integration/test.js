describe('tests lab3', () => {
    it('find email input', () => {
        cy.visit('https://www.pinterest.com/business/hub/?autologin=true');
        cy.get("button[data-test-id=\"page-scroll-arrow\"]:last-of-type").click();

        cy.get('#email').then((elem) => {
            if (!elem) {
                throw new Error("test fails here")
            }
        })
    });

    it('find password input', () => {
        cy.visit('https://www.pinterest.com/business/hub/?autologin=true');
        cy.get("button[data-test-id=\"page-scroll-arrow\"]:last-of-type").click();

        cy.get('input[name=password]').then((elem) => {
            if (!elem) {
                throw new Error("test fails here")
            }
        })
    });

    it('find submit button in sign in form', () => {
        cy.visit('https://www.pinterest.com/business/hub/?autologin=true');
        cy.get("button[data-test-id=\"page-scroll-arrow\"]:last-of-type").click();

        cy.get("form[data-test-id=registerForm] *[data-test-id=registerFormSubmitButton] button").then((elem) => {
            if (!elem) {
                throw new Error("test fails here")
            }
        })
    });

    it('find about page link', () => {
        cy.visit('https://www.pinterest.com/business/hub/?autologin=true');
        cy.get('a[href*=about]').then((elem) => {
            if (!elem) {
                throw new Error("test fails here")
            }
        })
    });

    it('find button with red text', () => {
        cy.visit('https://www.pinterest.com/business/hub/?autologin=true');
        cy.get('button[style*="color:red"]').then((elem) => {
            if (!elem) {
                throw new Error("test fails here")
            }
        })
    });
});
