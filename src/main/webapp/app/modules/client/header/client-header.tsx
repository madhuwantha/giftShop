import React from 'react';
import { IHeaderProps } from 'app/shared/layout/header/header';
import { Button } from 'reactstrap';
import { Link } from 'react-router-dom';

const baseHref = document.querySelector('base').getAttribute('href').replace(/\/$/, '');

const ClientHeader = (props: IHeaderProps) => {
  return (
    <div>
      <header className="main-header-area">
        <div className="main-header header-sticky">
          <div className="container custom-area">
            <div className="row align-items-center">
              <div className="col-lg-2 col-xl-2 col-md-6 col-6 col-custom">
                <div className="header-logo d-flex align-items-center">
                  <Button style={{ background: 'transparent', border: 'none' }} tag={Link} to={`${baseHref}/shop/home`}>
                    <img className="img-full" src="../../../content/images/logo/logo.png" alt="ClientHeader Logo" />
                  </Button>
                </div>
              </div>
              <div className="col-lg-8 d-none d-lg-flex justify-content-center col-custom">
                <nav className="main-nav d-none d-lg-flex">
                  <ul className="nav">
                    <li>
                      <Button style={{ background: 'transparent', border: 'none' }} tag={Link} to={`${baseHref}/shop/home`}>
                        <span className="menu-text"> Home</span>
                      </Button>
                    </li>
                    {props.isAuthenticated ? (
                      <li>
                        <Button style={{ background: 'transparent', border: 'none' }} tag={Link} to={`${baseHref}/shop/cart`}>
                          <span className="menu-text"> Cart</span>
                        </Button>
                      </li>
                    ) : (
                      <></>
                    )}
                    <li>
                      <a href="#">
                        <span className="menu-text"> Account</span>
                      </a>
                      <ul className="dropdown-submenu dropdown-hover">
                        {props.isAuthenticated ? (
                          <>
                            <li>
                              <Button style={{ background: 'transparent', border: 'none' }} tag={Link} to={`${baseHref}/logout`}>
                                <span className="menu-text"> Sign out</span>
                              </Button>
                            </li>
                          </>
                        ) : (
                          <>
                            <li>
                              <Button style={{ background: 'transparent', border: 'none' }} tag={Link} to={`${baseHref}/login`}>
                                <span className="menu-text">Login</span>
                              </Button>
                            </li>
                            <li>
                              <Button style={{ background: 'transparent', border: 'none' }} tag={Link} to={`${baseHref}/account/register`}>
                                <span className="menu-text">Register</span>
                              </Button>
                            </li>
                          </>
                        )}
                      </ul>
                    </li>
                  </ul>
                </nav>
              </div>
            </div>
          </div>
        </div>
      </header>

      <div className="breadcrumbs-area position-relative">
        <div className="container">
          <div className="row">
            <div className="col-12 text-center">
              <div className="breadcrumb-content position-relative section-content">
                <h3 className="title-3">Flow Sun</h3>
                <ul>{/*<li><a href="index.html">Home</a></li>*/}</ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ClientHeader;
