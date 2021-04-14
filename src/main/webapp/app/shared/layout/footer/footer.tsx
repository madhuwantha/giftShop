import './footer.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Button } from 'reactstrap';

const baseHref = document.querySelector('base').getAttribute('href').replace(/\/$/, '');

const Footer = props => {
  return (
    <footer style={{ padding: 0 }} className="footer-area mt-text-3">
      <div className="footer-widget-area">
        <div className="container container-default custom-area">
          <div className="row">
            <div className="col-12 col-sm-12 col-md-12 col-lg-3 col-custom">
              <div className="single-footer-widget m-0">
                <div className="footer-logo">
                  <Button style={{ background: 'transparent', border: 'none' }} tag={Link} to={`${baseHref}/shop/home`}>
                    <img className="img-full" src="../../../content/images/logo/logo.png" alt="ClientHeader Logo" />
                  </Button>
                </div>
                <p className="desc-content">
                  Lorem Khaled Ipsum is a major key to success. To be successful you’ve got to work hard you’ve got to make it.
                </p>
                <div className="social-links"></div>
              </div>
            </div>
            <div className="col-12 col-sm-6 col-md-6 col-lg-2 col-custom">
              {/*  <div className="single-footer-widget">*/}
              {/*    <h2 className="widget-title">Information</h2>*/}
              {/*    <ul className="widget-list">*/}
              {/*      <li><a href="about-us.html">Our Company</a></li>*/}
              {/*      <li><a href="contact-us.html">Contact Us</a></li>*/}
              {/*      <li><a href="about-us.html">Our Services</a></li>*/}
              {/*      <li><a href="about-us.html">Why We?</a></li>*/}
              {/*      <li><a href="about-us.html">Careers</a></li>*/}
              {/*    </ul>*/}
              {/*  </div>*/}
            </div>
            <div className="col-12 col-sm-6 col-md-6 col-lg-2 col-custom">
              {/*  <div className="single-footer-widget">*/}
              {/*    <h2 className="widget-title">Quicklink</h2>*/}
              {/*    <ul className="widget-list">*/}
              {/*      <li><a href="about-us.html">About</a></li>*/}
              {/*      <li><a href="blog.html">Blog</a></li>*/}
              {/*      <li><a href="shop.html">Shop</a></li>*/}
              {/*      <li><a href="cart.html">Cart</a></li>*/}
              {/*      <li><a href="contact-us.html">Contact</a></li>*/}
              {/*    </ul>*/}
              {/*  </div>*/}
            </div>
            <div className="col-12 col-sm-6 col-md-6 col-lg-2 col-custom">
              {/*  <div className="single-footer-widget">*/}
              {/*    <h2 className="widget-title">Support</h2>*/}
              {/*    <ul className="widget-list">*/}
              {/*      <li><a href="contact-us.html">Online Support</a></li>*/}
              {/*      <li><a href="contact-us.html">Shipping Policy</a></li>*/}
              {/*      <li><a href="contact-us.html">Return Policy</a></li>*/}
              {/*      <li><a href="contact-us.html">Privacy Policy</a></li>*/}
              {/*      <li><a href="contact-us.html">Terms of Service</a></li>*/}
              {/*    </ul>*/}
              {/*  </div>*/}
            </div>
            <div className="col-12 col-sm-6 col-md-6 col-lg-3 col-custom">
              <div className="single-footer-widget">
                <h2 className="widget-title">See Information</h2>
                <div className="widget-body">
                  <address>123, ABC, Road ##, Main City, Your address goes here.Phone: 01234 567 890 Email: https://example.com</address>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="footer-copyright-area">
        <div className="container custom-area">
          <div className="row">
            <div className="col-12 text-center col-custom">
              <div className="copyright-content">
                <p>Copyright © 2021</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
