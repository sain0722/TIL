from sklearn.model_selection import GridSearchCV
from sklearn.ensemble import RandomForestClassifier

n_estimators = [10, 20, 30]
max_features = [2, 4]
bootstrap = [False]

param_grid = [
    {'n_estimators': n_estimators, 'max_features': max_features},
    {'bootstrap': bootstrap, 'n_estimators': n_estimators, 'max_features': max_features}
    ]
rf = RandomForestClassifier()

grid_search = GridSearchCV(rf, param_grid=param_grid, cv=2, scoring='mean_squared_error',
                           verbose=2, n_jobs=1, return_train_score=True)
# grid_search.fit(x_train, y_train)
