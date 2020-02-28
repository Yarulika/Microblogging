import React from 'react';
import { useHistory } from 'react-router-dom';

import Nav from '../Nav';
import SideNav from '../SideNav';
import { Container, Segment, Grid, Header } from 'semantic-ui-react';
import Post from '../Post';

const styles = {
  container: {
    paddingTop: '70px'
  }
};

const Wall = () => {
  const history = useHistory();

  const navigateTo = path => {
    history.push(path);
  };

  const handleSubmit = e => {
    e.preventDefault();
  };

  return (
    <>
      <Nav />
      {/* {Array.from(2).fill(<Post />)} */}

      <Container style={styles.container}>
        <Grid columns={2} stackable>
          <Grid.Row>
            <Grid.Column width={4}>
              <SideNav />
            </Grid.Column>
            <Grid.Column width={12} stretched>
              <Header>Recent posts</Header>
              <Post />
              <Post />
              <Post />
              <Post />
              <Post />
              <Post />
              <Post />
              <Post />
              <Post />
              <Post />
            </Grid.Column>
          </Grid.Row>
        </Grid>
      </Container>
    </>
  );
};

export default Wall;
