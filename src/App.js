import React from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';

import LoginPage from './components/Pages/LoginPage';
import SignupPage from './components/Pages/SignupPage';
import Home from './components/Pages/Home';
import Wall from './components/Pages/Wall';

function App() {
  return (
    <div>
      <Router>
        <Route path="/login" exact component={LoginPage} />
        <Route path="/signup" exact component={SignupPage} />
        <Route path="/home" exact component={Home} />
        <Route path="/wall" exact component={Wall} />
      </Router>
    </div>
  );
}

export default App;
