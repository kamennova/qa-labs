describe('tests lab3', () => {

    it('form validation - skip email input', () => {
        cy.visit('https://www.pinterest.com/');
        cy.get("*[data-test-id=simple-login-button] button").click();
        cy.get('#password').type("aaa");
        cy.get("*[data-test-id=registerFormSubmitButton]").click();
        cy.url().then(url => expect(url).to.equal("https://www.pinterest.com/")); // didn't redirect to user page
    });

    it('login with wrong password', () => {
        cy.visit('https://www.pinterest.com/');
        cy.get("*[data-test-id=simple-login-button] button").click();
        cy.get("#email").type("19soaring.unicorns@gmail.com");
        cy.get('#password').type("111");
        cy.get("*[data-test-id=registerFormSubmitButton]").click();
        cy.url().then(url => expect(url).to.equal("https://www.pinterest.com/")); // didn't redirect to user page
    });

    /*it('find contact page link', () => {
        cy.visit('https://www.pinterest.com');
        cy.get('a[href*=contact]').then((elem) => {
            if (!elem) {
                throw new Error("test fails here")
            }
        })
    });*/

    it('privacy policy / confidentiality links', () => {
        cy.visit('https://www.pinterest.com');
        cy.get("button[data-test-id=\"page-scroll-arrow\"]:last-of-type").click();
        cy.get('a[href*=terms-of-service]').then((elem) => {
            if (!elem) {
                throw new Error("test fails here")
            }
        });
        cy.get('a[href*=privacy-policy]').then((elem) => {
            if (!elem) {
                throw new Error("test fails here")
            }
        })
    });

    it('form autofocus on open', () => {
        cy.visit('https://www.pinterest.com');

        cy.get("*[data-test-id=simple-signup-button] button").click();
        cy.focused().should('have.attr', 'id', 'email');

        cy.get("*[data-test-id=full-page-signup-close-button] button").click();
        cy.get("*[data-test-id=simple-login-button] button").click();
        cy.focused().should('have.attr', 'id', 'email');
    });
});
