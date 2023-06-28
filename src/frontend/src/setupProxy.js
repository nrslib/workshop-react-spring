const {createProxyMiddleware} = require('http-proxy-middleware');

module.exports = function (app) {
    app.use(
        '/api',
        createProxyMiddleware({
            target: process.env.REACT_APP_PROXY_HOST,
            changeOrigin: true
        }),
    );
    app.use(
        '/swagger-ui',
        createProxyMiddleware({
            target: process.env.REACT_APP_PROXY_HOST,
            changeOrigin: true
        }),
    )
    app.use(
        '/v3',
        createProxyMiddleware({
            target: process.env.REACT_APP_PROXY_HOST,
            changeOrigin: true
        }),
    )
};