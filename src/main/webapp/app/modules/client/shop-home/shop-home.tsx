import React, { useEffect } from 'react';

import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button } from 'reactstrap';
import { IRootState } from 'app/shared/reducers';
import { getEntities, selectCategory } from 'app/entities/category/category.reducer';
import { getEntities as getGiftItems } from 'app/entities/gift-item/gift-item.reducer';
import GftItemCard from 'app/components/gft-item-card';

export interface IShopHomeProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ShopHome = (props: IShopHomeProps) => {
  useEffect(() => {
    props.getEntities();
    props.getGiftItems();
  }, []);

  const { categoryList, match, loading } = props;

  const onCategoryClicked = (id: number) => {
    props.selectCategory(id);
  };

  return (
    <div>
      <div className="shop-main-area">
        <div className="container container-default custom-area">
          <div className="row flex-row-reverse">
            <div className="col-lg-9 col-12 col-custom widget-mt">
              <div className="row shop_wrapper grid_3">
                {props.giftItemList.map((getGiftItem, idx) => {
                  return <GftItemCard giftItem={getGiftItem} {...props} key={idx} />;
                })}
              </div>
            </div>
            <div className="col-lg-3 col-12 col-custom">
              <aside className="sidebar_widget widget-mt">
                <div className="widget_inner">
                  <div className="widget-list widget-mb-1">
                    <h3 className="widget-title">Search</h3>
                    <div className="search-box">
                      <div className="input-group">
                        <input type="text" className="form-control" placeholder="Search Our Store" aria-label="Search Our Store" />
                        <div className="input-group-append">
                          <button className="btn btn-outline-secondary" type="button">
                            <i className="fa fa-search"></i>
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="widget-list widget-mb-1">
                    <h3 className="widget-title">Categories</h3>
                    <nav>
                      <ul className="mobile-menu p-0 m-0">
                        {categoryList.map(category => {
                          return (
                            <li key={category.id} onClick={() => onCategoryClicked(category.id)} className="menu-item-has-children">
                              <Button style={{ background: 'transparent', color: 'black', border: 'none' }}>
                                <span className="menu-text">{category.categoryName}</span>
                              </Button>
                            </li>
                          );
                        })}
                      </ul>
                    </nav>
                  </div>
                  <div className="widget-list widget-mb-1">
                    <h3 className="widget-title">Price Filter</h3>
                    <form action="#">
                      <div id="slider-range"></div>
                      <button type="submit">Filter</button>
                      <input type="text" name="text" id="amount" />
                    </form>
                  </div>
                </div>
              </aside>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

const mapStateToProps = ({ category, giftItem }: IRootState) => ({
  categoryList: category.entities,
  loading: category.loading,
  selectCategory: category.selectedCategory,
  giftItemList: giftItem.entities,
});

const mapDispatchToProps = {
  getEntities,
  selectCategory,
  getGiftItems,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ShopHome);
