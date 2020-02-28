import React from 'react';
import { Card, Image, Button, Icon } from 'semantic-ui-react';

const styles = {
  button: {
    background: 'none'
  }
};

const Post = () => {
  const images = ['molly.png', 'steve.jpg', 'jenny.jpg', 'matthew.png'];

  const randomIndex = () => {
    return Math.round(Math.random() * 3);
  };

  const imageSrc = `https://react.semantic-ui.com/images/avatar/large/${
    images[randomIndex()]
  }`;

  return (
    <Card fluid>
      <Card.Content>
        <Image circular bordered floated="left" size="mini" src={imageSrc} />
        <Card.Header>Steve Sanders</Card.Header>
        <Card.Meta>Feb 29 2020 at 15:06</Card.Meta>
        <Card.Description>
          The life is beautiful Lorem ipsum dolor sit amet consectetur
          adipisicing elit. Nulla, quis quo consectetur at quia soluta nesciunt
          facilis earum. Quidem dolores cumque minus, facilis quod minima.
          Praesentium veritatis blanditiis tenetur totam!
        </Card.Description>
      </Card.Content>
      <Card.Content>
        <Button style={styles.button}>
          <Icon name="heart outline" color="red" /> 3005
        </Button>
        <Button style={styles.button}>
          <Icon name="share square outline" color="blue" /> 215
        </Button>
        <Button floated="right" style={styles.button}>
          <Icon name="comment outline" color="blue" /> 215
        </Button>
      </Card.Content>
    </Card>
  );
};

export default Post;
