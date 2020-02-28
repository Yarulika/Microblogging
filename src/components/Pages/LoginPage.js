import React from 'react';
import { useHistory } from 'react-router-dom';
import {
  Button,
  Form,
  Grid,
  Header,
  Icon,
  Message,
  Segment
} from 'semantic-ui-react';

const styles = {
  color: 'blue',
  buttonLink: {
    color: 'blue',
    background: 'none',
    padding: 0,
    fontWeight: 100
  }
};

const LoginPage = () => {
  const history = useHistory();

  const navigateTo = path => {
    history.push(path);
  };

  const handleSubmit = e => {
    e.preventDefault();
  };

  return (
    <Grid textAlign="center" style={{ height: '100vh' }} verticalAlign="middle">
      <Grid.Column style={{ maxWidth: 450 }}>
        <Header color={styles.color} content="Microblogging" as="h1" />
        <Header color={styles.color} icon>
          <Icon name="twitter" />
          <Header.Content>
            Log In
            <Header.Subheader>Start Microblogging</Header.Subheader>
          </Header.Content>
        </Header>
        <Form size="large" onSubmit={handleSubmit}>
          <Segment basic>
            <Form.Input
              fluid
              icon="at"
              iconPosition="left"
              placeholder="E-mail address"
            />
            <Form.Input
              fluid
              icon="lock"
              iconPosition="left"
              placeholder="Password"
              type="password"
            />

            <Button color={styles.color} fluid size="large" type="submit">
              Login
            </Button>

            <Message>
              New to us?{' '}
              <Button
                style={styles.buttonLink}
                onClick={() => navigateTo('/signup')}
              >
                Sign Up
              </Button>
            </Message>
          </Segment>
        </Form>
      </Grid.Column>
    </Grid>
  );
};

export default LoginPage;
